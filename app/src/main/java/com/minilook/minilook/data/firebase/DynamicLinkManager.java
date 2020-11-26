package com.minilook.minilook.data.firebase;

import android.content.Context;
import android.net.Uri;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;

public class DynamicLinkManager {

    public static final String TYPE_PRODUCT = "product";
    public static final String TYPE_BRAND = "brand";
    public static final String TYPE_EVENT = "event";
    public static final String TYPE_PROMOTION = "promotion";
    public static final String TYPE_PREORDER = "preorder";

    private Context context;

    public DynamicLinkManager(Context context) {
        this.context = context;
    }

    private DynamicLink.Builder createDynamicLink(Context context) {
        return FirebaseDynamicLinks.getInstance().createDynamicLink()
            .setDomainUriPrefix("http://minilook.page.link")
            .setAndroidParameters(new DynamicLink.AndroidParameters.Builder(context.getPackageName()).build())
            .setIosParameters(new DynamicLink.IosParameters.Builder("co.kr.minilook-ios")
                .setAppStoreId("1526261228")
                .setFallbackUrl(Uri.parse("https://itunes.apple.com/us/app/id1526261228"))
                .build())
            .setNavigationInfoParameters(
                new DynamicLink.NavigationInfoParameters.Builder().setForcedRedirectEnabled(true).build());
    }

    private Uri createDeepLink(String type, int no) {
        return new Uri.Builder()
            .scheme("https")
            .authority("www.minilook.co.kr")
            .appendQueryParameter("type", type)
            .appendQueryParameter("id", String.valueOf(no))
            .build();
    }

    public void createShareLink(String type, int no, String desc, String imgUrl, OnCompletedListener listener) {
        createDynamicLink(context)
            .setLink(createDeepLink(type, no))
            .setSocialMetaTagParameters(createMetaData(type, desc, imgUrl))
            .buildShortDynamicLink()
            .addOnCompleteListener(task -> {
                if (task.isSuccessful() && task.getResult() != null) {
                    Uri shortLink = task.getResult().getShortLink();
                    listener.onSuccess(shortLink);
                } else {
                    listener.onFail();
                }
            });
    }

    private DynamicLink.SocialMetaTagParameters createMetaData(String type, String desc, String imgUrl) {
        return new DynamicLink.SocialMetaTagParameters.Builder()
            .setTitle(getTitle(type))
            .setDescription(desc)
            .setImageUrl(Uri.parse(imgUrl))
            .build();
    }

    private String getTitle(String type) {
        switch (type) {
            default:
            case TYPE_PRODUCT:
                return "미니룩";
            case TYPE_BRAND:
                return "미니룩 브랜드샵";
            case TYPE_EVENT:
                return "미니룩 이벤트";
            case TYPE_PROMOTION:
                return "미니룩 기획전";
            case TYPE_PREORDER:
                return "미니룩 프리오더";
        }
    }

    public interface OnCompletedListener {
        void onSuccess(Uri uri);

        void onFail();
    }
}
