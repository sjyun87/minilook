package com.minilook.minilook.util;

import android.net.Uri;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.minilook.minilook.App;

public class DynamicLinkUtil {

    public static final String TYPE_PRODUCT = "product";
    public static final String TYPE_BRAND = "brand";
    public static final String TYPE_EVENT = "event";
    public static final String TYPE_PROMOTION = "promotion";
    public static final String TYPE_PREORDER = "preorder";
    public static final String TYPE_CHALLENGE = "challenge";

    public void createLink(String type, int no, String desc, String imgUrl, OnDynamicLinkListener listener) {
        createDynamicLink()
            .setLink(createDeepLink(type, no))
            .setSocialMetaTagParameters(createMetaData(type, desc, imgUrl))
            .buildShortDynamicLink()
            .addOnCompleteListener(task -> {
                if (task.isSuccessful() && task.getResult() != null) {
                    Uri shortLink = task.getResult().getShortLink();
                    if (shortLink != null) {
                        listener.onSuccess(shortLink.toString());
                    } else {
                        listener.onError();
                    }
                } else {
                    listener.onError();
                }
            });
    }

    private DynamicLink.Builder createDynamicLink() {
        DynamicLink.AndroidParameters androidParameters =
            new DynamicLink.AndroidParameters
                .Builder(App.getInstance().getPackageName())
                .build();
        DynamicLink.IosParameters iosParameters =
            new DynamicLink.IosParameters.Builder("co.kr.minilook-ios")
                .setAppStoreId("1526261228")
                .setFallbackUrl(Uri.parse("https://itunes.apple.com/us/app/id1526261228"))
                .build();
        DynamicLink.NavigationInfoParameters navigationInfoParameters =
            new DynamicLink.NavigationInfoParameters.Builder()
                .setForcedRedirectEnabled(true)
                .build();

        return FirebaseDynamicLinks.getInstance().createDynamicLink()
            .setDomainUriPrefix("http://minilook.page.link")
            .setAndroidParameters(androidParameters)
            .setIosParameters(iosParameters)
            .setNavigationInfoParameters(navigationInfoParameters);
    }

    private Uri createDeepLink(String type, int no) {
        return new Uri.Builder()
            .scheme("https")
            .authority("www.minilook.co.kr")
            .appendQueryParameter("type", type)
            .appendQueryParameter("id", String.valueOf(no))
            .build();
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

    public interface OnDynamicLinkListener {
        void onSuccess(String link);

        void onError();
    }
}
