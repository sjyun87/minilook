package com.minilook.minilook.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import timber.log.Timber;

public class HashKeyUtil {

    @SuppressLint("PackageManagerGetSignatures")
    public static void getHashKey(Context context) {
        PackageInfo packageInfo = null;
        try {
            packageInfo =
                context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null) {
            Timber.e("KeyHash :: KeyHash is null...");
        }

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Timber.e("KeyHash :: %s", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            } catch (NoSuchAlgorithmException e) {
                Timber.e("KeyHash :: " + "Unable to get MessageDigest. signature=" + signature + "Error = " + e);
            }
        }
    }
}
