package com.minilook.minilook.data.network.base;

import android.content.res.Resources;
import androidx.core.os.ConfigurationCompat;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.minilook.minilook.BuildConfig;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class BaseRequest<T> {
    private final static int TIMEOUT_SECONDS = 10;

    public T getApi() {
        return createRetrofit().create(getService());
    }

    protected abstract Class<T> getService();

    private Retrofit createRetrofit() {
        return new Retrofit.Builder()
            .baseUrl(getBaseUrl())
            .addConverterFactory(GsonConverterFactory.create(getGson()))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(createClient())
            .build();
    }

    private String getBaseUrl() {
        return EndPoint.BASE_URL.getValue();
    }

    private Gson getGson() {
        return new GsonBuilder().setPrettyPrinting().create();
    }

    private OkHttpClient createClient() {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS);
        client.connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS);
        client.addInterceptor(chain -> {
            Request original = chain.request();
            Request.Builder requestBuilder = original.newBuilder();

            Map<String, String> header = createHeaders();
            addHeader(header, requestBuilder);

            return chain.proceed(requestBuilder.build());
        });

        if (isInterceptor()) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            client.addInterceptor(interceptor);
        }
        return client.build();
    }

    private Map<String, String> createHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        //headers.put("x-api-key", "OLbxaJ2C6BadAsa9fBe9Ja4WUegvpDnB17urafki");
        //headers.put("country", getCountry());
        //headers.put("language", getLanguage());
        //headers.put("os", "aos");
        //headers.put("token", getToken());
        return headers;
    }

    private void addHeader(@NonNull Map<String, String> headers, @NonNull Request.Builder requestBuilder) {
        for (Map.Entry<String, String> headerEntry : headers.entrySet()) {
            requestBuilder.addHeader(headerEntry.getKey(), headerEntry.getValue());
        }
    }

    private String getLanguage() {
        //return getLocale().getLanguage();
        return "ko";
    }

    private String getCountry() {
        return getLocale().getCountry();
    }

    private Locale getLocale() {
        return ConfigurationCompat.getLocales(Resources.getSystem().getConfiguration()).get(0);
    }

    protected boolean isInterceptor() {
        return BuildConfig.DEBUG;
    }

    protected RequestBody createRequestBody(Map<String, Object> bodyMap) {
        return createRequestBody(new Gson().toJsonTree(bodyMap).toString());
    }

    protected RequestBody createRequestBody(JsonElement json) {
        return createRequestBody(json.toString());
    }

    protected RequestBody createRequestBody(String json) {
        return RequestBody.create(MediaType.parse("application/json"), json);
    }

    @AllArgsConstructor @Getter protected enum EndPoint {
        BASE_URL(BuildConfig.DEBUG ?
            "http://dev.app.api.minilook.co.kr:8080" :
            "http://app.api.minilook.co.kr:8080");

        private String value;
    }
}
