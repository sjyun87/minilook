package com.minilook.minilook.data.network.naver;

import com.minilook.minilook.App;
import com.minilook.minilook.data.model.common.KeyDataModel;
import com.minilook.minilook.data.model.gallery.PhotoDataModel;
import com.minilook.minilook.data.network.base.BaseRequest;
import io.reactivex.rxjava3.core.Single;
import java.io.File;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import timber.log.Timber;

public class NCloudRequest extends BaseRequest<NCloudService> {

    public static final String TYPE_REVIEW = "reviews";
    public static final String TYPE_QUESTION = "inquiries";

    private static final String HOST = "kr.object.ncloudstorage.com";

    private static final String CHARSET_NAME = "UTF-8";
    private static final String HMAC_ALGORITHM = "HmacSHA256";
    private static final String HASH_ALGORITHM = "SHA-256";
    private static final String AWS_ALGORITHM = "AWS4-HMAC-SHA256";

    private static final String SERVICE_NAME = "s3";
    private static final String REQUEST_TYPE = "aws4_request";

    private static final String REGION_NAME = "kr-standard";
    private static final String UNSIGNED_PAYLOAD = "UNSIGNED-PAYLOAD";

    @Override protected Class<NCloudService> getService() {
        return NCloudService.class;
    }

    @Override protected String getBaseUrl() {
        return EndPoint.NAVER_CLOUD_URL.getValue();
    }

    public Single<ResponseBody> putImage(String type, KeyDataModel keys, int productNo, PhotoDataModel imageData) {
        Date now = new Date();
        int memberNo = App.getInstance().getMemberNo();
        String objectName = createObjectName(now, productNo);
        String uploadPath = getUploadPath(type, memberNo, objectName);
        return getApi().putReviewImage(createAuthHeaders(keys, uploadPath, now), memberNo, objectName,
            createImageData(new File(imageData.getFilePath())));
    }

    private static String createObjectName(Date date, int productNo) {
        StringBuilder sb = new StringBuilder();
        sb.append("P");
        sb.append(productNo);
        sb.append("_");
        sb.append(new SimpleDateFormat("yyyyMMddHHmmssSSSS", Locale.getDefault()).format(date));
        sb.append(".png");
        return sb.toString();
    }

    private RequestBody createImageData(File file) {
        return RequestBody.create(file, MediaType.parse("image/png"));
    }

    private String getUploadPath(String type, int memberNo, String objectName) {
        return "/minilook/" + type + "/" + memberNo + "/" + objectName;
    }

    private Map<String, String> createAuthHeaders(KeyDataModel keys, String uploadPath, Date now) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Host", HOST);
        headers.put("X-Amz-Date", getTimeStamp(now));
        headers.put("X-Amz-Content-Sha256", UNSIGNED_PAYLOAD);
        headers.put("Authorization", createAuthorization(keys, uploadPath, headers, now));
        return headers;
    }

    private String createAuthorization(KeyDataModel keys, String uploadPath, Map<String, String> headers, Date date) {
        TreeMap<String, String> sortedHeaders = getSortedHeaders(headers);
        String signedHeaders = getSignedHeaders(sortedHeaders);
        String canonicalHeaders = getCanonicalHeaders(sortedHeaders);
        String canonicalRequest = getCanonicalRequest(uploadPath, canonicalHeaders, signedHeaders);
        String scope = getScope(date);
        String stringToSign = getStringToSign(getTimeStamp(date), scope, canonicalRequest);
        String signature = getSignature(keys.getSecretKey(), date, stringToSign);
        String authorization = getAuthorization(keys.getAccessKey(), scope, signedHeaders, signature);
        return authorization;
    }

    private static TreeMap<String, String> getSortedHeaders(Map<String, String> headers) {
        TreeMap<String, String> sortedHeaders = new TreeMap<>();
        for (String key : headers.keySet()) {
            sortedHeaders.put(key, headers.get(key));
        }
        return sortedHeaders;
    }

    private static String getSignedHeaders(TreeMap<String, String> sortedHeaders) {
        StringBuilder signedHeadersBuilder = new StringBuilder();
        for (String headerName : sortedHeaders.keySet()) {
            signedHeadersBuilder.append(headerName.toLowerCase()).append(";");
        }
        return signedHeadersBuilder.toString();
    }

    private static String getCanonicalHeaders(TreeMap<String, String> sortedHeaders) {
        StringBuilder standardizedHeadersBuilder = new StringBuilder();
        for (String headerName : sortedHeaders.keySet()) {
            standardizedHeadersBuilder
                .append(headerName.toLowerCase())
                .append(":")
                .append(sortedHeaders.get(headerName))
                .append("\n");
        }
        return standardizedHeadersBuilder.toString();
    }

    private static String getCanonicalRequest(String uploadPath, String canonicalHeaders, String signedHeaders) {
        return "PUT" + "\n"
            + uploadPath + "\n"
            + "\n"
            + canonicalHeaders + "\n"
            + signedHeaders + "\n"
            + UNSIGNED_PAYLOAD;
    }

    private static String getScope(Date date) {
        return getDateStamp(date) + "/"
            + REGION_NAME + "/"
            + SERVICE_NAME + "/"
            + REQUEST_TYPE;
    }

    private static String getStringToSign(String timestamp, String scope, String canonicalRequest) {
        return AWS_ALGORITHM + "\n"
            + timestamp + "\n"
            + scope + "\n"
            + hash(canonicalRequest);
    }

    private static String getSignature(String secretKey, Date date, String stringToSign) {
        try {
            byte[] kSecret = ("AWS4" + secretKey).getBytes(CHARSET_NAME);
            byte[] kDate = sign(getDateStamp(date), kSecret);
            byte[] kRegion = sign(REGION_NAME, kDate);
            byte[] kService = sign(SERVICE_NAME, kRegion);
            byte[] signingKey = sign(REQUEST_TYPE, kService);
            return toHax(sign(stringToSign, signingKey));
        } catch (Exception e) {
            Timber.e(e);
            return null;
        }
    }

    private static String getAuthorization(String accessKey, String scope, String signedHeaders, String signature) {
        String signingCredentials = accessKey + "/" + scope;
        String credential = "Credential=" + signingCredentials;
        String signerHeaders = "SignedHeaders=" + signedHeaders;
        String signatureHeader = "Signature=" + signature;

        return AWS_ALGORITHM + " "
            + credential + ", "
            + signerHeaders + ", "
            + signatureHeader;
    }

    private static String getTimeStamp(Date date) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'", Locale.getDefault());
        timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return timeFormat.format(date);
    }

    private static String getDateStamp(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.format(date);
    }

    private static String toHax(byte[] byteData) {
        StringBuilder sb = new StringBuilder();
        for (byte byteDatum : byteData) {
            sb.append(Integer.toString((byteDatum & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    private static String hash(String text) {
        try {
            MessageDigest e = MessageDigest.getInstance(HASH_ALGORITHM);
            e.update(text.getBytes(CHARSET_NAME));
            return toHax(e.digest());
        } catch (Exception e) {
            Timber.e(e);
            return null;
        }
    }

    private static byte[] sign(String stringData, byte[] key) {
        try {
            byte[] data = stringData.getBytes(CHARSET_NAME);
            Mac e = Mac.getInstance(HMAC_ALGORITHM);
            e.init(new SecretKeySpec(key, HMAC_ALGORITHM));
            return e.doFinal(data);
        } catch (Exception e) {
            Timber.e(e);
            return null;
        }
    }
}
