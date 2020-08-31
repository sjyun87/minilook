package com.minilook.minilook;

import android.app.Application;

import android.content.ContextWrapper;
import butterknife.BindString;
import butterknife.ButterKnife;
import com.kakao.sdk.common.KakaoSdk;
import com.pixplicity.easyprefs.library.Prefs;
import lombok.Getter;
import lombok.Setter;
import timber.log.Timber;

public class App extends Application {

    @Getter private static App instance;
    @Getter @Setter private boolean isLogin = false;
    @Getter @Setter private int memberNo;

    @Override public void onCreate() {
        super.onCreate();

        init();
    }

    private void init() {
        instance = this;
        setupTimber();
        setupKakao();
        setupPreference();
    }

    private void setupTimber() {
        if (BuildConfig.DEBUG) Timber.plant(new Timber.DebugTree());
    }

    private void setupKakao() {
        KakaoSdk.init(this, getString(R.string.kakao_app_key));
    }

    private void setupPreference() {
        new Prefs.Builder().setContext(this)
            .setMode(ContextWrapper.MODE_PRIVATE)
            .setPrefsName(getPackageName())
            .setUseDefaultSharedPreference(true)
            .build();
    }
}
