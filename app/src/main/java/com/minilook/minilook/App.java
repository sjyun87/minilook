package com.minilook.minilook;

import android.app.Application;
import android.content.ContextWrapper;
import com.kakao.sdk.common.KakaoSdk;
import com.minilook.minilook.data.common.PrefsKey;
import com.minilook.minilook.data.model.common.SortDataModel;
import com.minilook.minilook.data.model.pick.PickBrandDataModel;
import com.minilook.minilook.data.model.user.UserDataModel;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.data.rx.RxBusEvent;
import com.pixplicity.easyprefs.library.Prefs;
import java.util.List;
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
    @Getter @Setter private List<SortDataModel> sortCodes;
    @Getter @Setter private List<PickBrandDataModel> orderItems;

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

    public void setupLogin(UserDataModel data) {
        this.isLogin = true;
        setUserId(data.getUser_id());
        setSnsId(data.getSns_id());
        setSnsType(data.getType());
        Prefs.putInt(PrefsKey.KEY_LOGIN_VISIBLE_COUNT, 3);
        RxBus.send(new RxBusEvent.RxBusEventLogin());
    }

    public void setupLogout() {
        this.isLogin = false;
        clearUserId();
        clearSnsId();
        clearSnsType();
        RxBus.send(new RxBusEvent.RxBusEventLogout());
    }

    public void checkLogin() {
        if (getUserId() != -1) {
            isLogin = true;
        } else {
            isLogin = false;
        }
    }

    public void setUserId(int id) {
        userId = id;
        Prefs.putInt("userId", id);
    }

    public int getUserId() {
        userId = Prefs.getInt("userId", -1);
        return userId;
    }

    public void clearUserId() {
        userId = -1;
        Prefs.remove("userId");
    }

    public void setSnsId(String id) {
        snsId = id;
        Prefs.putString("snsId", id);
    }

    public String getSnsId() {
        snsId = Prefs.getString("snsId", "");
        return snsId;
    }

    public void clearSnsId() {
        snsId = null;
        Prefs.remove("snsId");
    }

    public void setSnsType(String type) {
        snsType = type;
        Prefs.putString("snsType", type);
    }

    public String getSnsType() {
        snsType = Prefs.getString("snsType", "");
        return snsType;
    }

    public void clearSnsType() {
        snsType = null;
        Prefs.remove("snsType");
    }

    public void setPushToken(String token) {
        pushToken = token;
        Prefs.putString("pushToken", pushToken);
    }

    public String getPushToken() {
        pushToken = Prefs.getString("pushToken", "");
        return pushToken;
    }
}
