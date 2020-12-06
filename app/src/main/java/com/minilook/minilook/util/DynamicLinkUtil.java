package com.minilook.minilook.util;

import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.minilook.minilook.App;
import com.minilook.minilook.R;

public class DynamicLinkUtil {

    public static final String TYPE_PRODUCT = "product";
    public static final String TYPE_BRAND = "brand";
    public static final String TYPE_EVENT = "event";
    public static final String TYPE_PROMOTION = "promotion";
    public static final String TYPE_PREORDER = "preorder";

    public static void sendDynamicLink(String type, int no, String desc, String imgUrl) {
        createDynamicLink()
            .setLink(createDeepLink(type, no))
            .setSocialMetaTagParameters(createMetaData(type, desc, imgUrl))
            .buildShortDynamicLink()
            .addOnCompleteListener(task -> {
                if (task.isSuccessful() && task.getResult() != null) {
                    Uri shortLink = task.getResult().getShortLink();
                    if (shortLink != null) {
                        send(shortLink.toString());
                    } else {
                        showErrorMessage();
                    }
                } else {
                    showErrorMessage();
                }
            });
    }

    private static DynamicLink.Builder createDynamicLink() {
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

    private static Uri createDeepLink(String type, int no) {
        return new Uri.Builder()
            .scheme("https")
            .authority("www.minilook.co.kr")
            .appendQueryParameter("type", type)
            .appendQueryParameter("id", String.valueOf(no))
            .build();
    }

    private static DynamicLink.SocialMetaTagParameters createMetaData(String type, String desc, String imgUrl) {
        return new DynamicLink.SocialMetaTagParameters.Builder()
            .setTitle(getTitle(type))
            .setDescription(desc)
            .setImageUrl(Uri.parse(imgUrl))
            .build();
    }

    private static String getTitle(String type) {
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

    private static void send(String link) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, link);
        App.getInstance().startActivity(Intent.createChooser(intent, "친구에게 공유하기"));
    }

    private static void showErrorMessage() {
        String message = App.getInstance().getResources().getString(R.string.dialog_error_title);
        Toast.makeText(App.getInstance(), message, Toast.LENGTH_SHORT).show();
    }
}
