package com.minilook.minilook;

import android.app.Application;
import android.content.ContextWrapper;
import com.kakao.sdk.common.KakaoSdk;
import com.minilook.minilook.data.common.PrefsKey;
import com.minilook.minilook.data.model.common.CodeDataModel;
import com.minilook.minilook.data.model.shopping.ShoppingBrandDataModel;
import com.minilook.minilook.data.model.user.UserDataModel;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.data.rx.RxBusEvent;
import com.pixplicity.easyprefs.library.Prefs;
import java.util.ArrayList;
import java.util.List;
import kr.co.bootpay.BootpayAnalytics;
import lombok.Getter;
import lombok.Setter;
import timber.log.Timber;

public class App extends Application {

    @Getter private static App instance;
    @Getter @Setter private boolean isLogin = false;
    @Getter @Setter private List<CodeDataModel> sortCodes;

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
        setupBootPay();
        isLogin = getMemberId() != -1;
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

    private void setupBootPay() {
        BootpayAnalytics.init(this,
            BuildConfig.DEBUG ? getString(R.string.bootpay_key_debug) : getString(R.string.bootpay_key_release));
    }









    public void setupLogin(UserDataModel data) {
        this.isLogin = true;
        setMemberId(data.getUser_id());
        setSnsId(data.getSns_id());
        setSnsType(data.getType());
        Prefs.putInt(PrefsKey.KEY_LOGIN_VISIBLE_COUNT, 3);
        RxBus.send(new RxBusEvent.RxBusEventLogin());
    }

    public void setupLogout() {
        this.isLogin = false;
        clearMemberId();
        clearSnsId();
        clearSnsType();
        RxBus.send(new RxBusEvent.RxBusEventLogout());
    }

    public void setMemberId(int id) {
        userId = id;
        Prefs.putInt("userId", id);
    }

    public int getMemberId() {
        userId = Prefs.getInt("userId", -1);
        return userId;
    }

    public void clearMemberId() {
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

    private List<ShoppingBrandDataModel> orderItems;

    public void setOrderItem(List<ShoppingBrandDataModel> items) {
        orderItems = new ArrayList<>();
        orderItems.addAll(items);
    }

    public List<ShoppingBrandDataModel> getOrderItems() {
        List<ShoppingBrandDataModel> items = new ArrayList<>(orderItems);
        orderItems.clear();
        return items;
    }
}
