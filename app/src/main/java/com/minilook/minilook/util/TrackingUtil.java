package com.minilook.minilook.util;

import android.content.Context;
import android.os.Bundle;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.minilook.minilook.BuildConfig;

public class TrackingUtil {

    private static FirebaseAnalytics firebaseAnalytics;

    public static void init(Context context) {
        firebaseAnalytics = FirebaseAnalytics.getInstance(context);
    }

    public static void pageTracking(String pageName, String className) {
        if (BuildConfig.DEBUG) return;
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, pageName);
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, className);
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);
    }
}
