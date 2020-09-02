package com.minilook.minilook;

import android.app.Application;
import android.content.ContextWrapper;
import com.kakao.sdk.common.KakaoSdk;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.ui.base.BaseActivity;
import com.pixplicity.easyprefs.library.Prefs;
import lombok.Getter;
import lombok.Setter;
import timber.log.Timber;

public class App extends Application {

    @Getter private static App instance;
    @Getter @Setter private boolean isLogin = false;
    private int userId;
    private String snsId;
    private String snsType;
    private String pushToken;

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

    public void setUserId(int id) {
        userId = id;
        Prefs.putInt("userId", id);
        RxBus.send(new BaseActivity.RxEventLogin());
    }

    public int getUserId() {
        userId = Prefs.getInt("userId", -1);
        return userId;
    }

    public void clearUserId() {
        userId = -1;
        Prefs.remove("userId");
        RxBus.send(new BaseActivity.RxEventLogout());
    }

    public void setSnsId(String id) {
        snsId = id;
        Prefs.putString("snsId", id);
    }

    public String getSnsId() {
        snsId = Prefs.getString("snsId", "");
        return snsId;
    }

    public void setSnsType(String type) {
        snsType = type;
        Prefs.putString("snsType", type);
    }

    public String getSnsType() {
        snsType = Prefs.getString("snsType", "");
        return snsType;
    }

    public void setPushToken(String token) {
        pushToken = token;
        Prefs.putString("pushToken", pushToken);
    }

    public String getPushToken() {
        pushToken = Prefs.getString("PushToken", "");
        return pushToken;
    }
}
