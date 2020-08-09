package com.minilook.minilook;

import android.app.Application;

import butterknife.BindString;
import com.kakao.sdk.common.KakaoSdk;
import timber.log.Timber;

public class App extends Application {

    @Override public void onCreate() {
        super.onCreate();

        init();
    }

    private void init() {
        if (BuildConfig.DEBUG) Timber.plant(new Timber.DebugTree());

        KakaoSdk.init(this, getString(R.string.kakao_app_key));
    }
}
