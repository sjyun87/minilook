package com.minilook.minilook;

import android.app.Application;
import android.content.ContextWrapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kakao.sdk.common.KakaoSdk;
import com.minilook.minilook.data.common.PrefsKey;
import com.minilook.minilook.data.model.common.CodeDataModel;
import com.minilook.minilook.data.model.member.MemberDataModel;
import com.minilook.minilook.data.model.shopping.ShoppingBrandDataModel;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.data.rx.RxBusEvent;
import com.minilook.minilook.util.TrackingManager;
import com.pixplicity.easyprefs.library.Prefs;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kr.co.bootpay.BootpayAnalytics;
import lombok.Getter;
import lombok.Setter;
import timber.log.Timber;

public class App extends Application {

    @Getter private static App instance;
    @Getter private Gson gson;

    @Getter @Setter private boolean isLogin = false;

    @Getter private boolean isDynamicLink = false;
    @Getter private Map<String, String> dynamicLinkData;

    @Getter @Setter private List<CodeDataModel> sortCodes;

    @Override public void onCreate() {
        super.onCreate();

        init();
    }

    private void init() {
        instance = this;
        setupGson();
        setupTimber();
        setupFA();
        setupKakao();
        setupPreference();
        setupBootPay();
        setupLoinData();
    }

    private void setupGson() {
        gson = new GsonBuilder()
            .setPrettyPrinting()
            //.excludeFieldsWithoutExposeAnnotation()
            .create();
    }

    private void setupTimber() {
        if (BuildConfig.DEBUG) Timber.plant(new Timber.DebugTree());
    }

    private void setupFA() {
        TrackingManager.init(getApplicationContext());
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

    private void setupLoinData() {
        isLogin = Prefs.contains(PrefsKey.KEY_MEMBER_NO);
    }

    public void setDynamicLink(String type, String id) {
        isDynamicLink = true;
        dynamicLinkData = new HashMap<>();
        dynamicLinkData.put("type", type);
        dynamicLinkData.put("id", id);
    }

    public void setPushToken(String token) {
        Prefs.putString(PrefsKey.KEY_PUSH_TOKEN, token);
    }

    public String getPushToken() {
        return Prefs.getString(PrefsKey.KEY_PUSH_TOKEN, "");
    }

    public void setupLogin(MemberDataModel data) {
        this.isLogin = true;
        setMemberNo(data.getMemberNo());
        setSnsId(data.getSnsId());
        setSnsType(data.getType());
        RxBus.send(new RxBusEvent.RxBusEventLogin());
    }

    public void setupLogout() {
        this.isLogin = false;
        clearMemberNo();
        clearSnsId();
        clearSnsType();
        RxBus.send(new RxBusEvent.RxBusEventLogout());
    }

    public void setMemberNo(int memberNo) {
        Prefs.putInt(PrefsKey.KEY_MEMBER_NO, memberNo);
    }

    public int getMemberNo() {
        return Prefs.getInt(PrefsKey.KEY_MEMBER_NO, -1);
    }

    public void clearMemberNo() {
        Prefs.remove(PrefsKey.KEY_MEMBER_NO);
    }

    public void setSnsId(String id) {
        Prefs.putString(PrefsKey.KEY_SNS_ID, id);
    }

    public String getSnsId() {
        return Prefs.getString(PrefsKey.KEY_SNS_ID, "");
    }

    public void clearSnsId() {
        Prefs.remove(PrefsKey.KEY_SNS_ID);
    }

    public void setSnsType(String type) {
        Prefs.putString(PrefsKey.KEY_SNS_TYPE, type);
    }

    public String getSnsType() {
        return Prefs.getString(PrefsKey.KEY_SNS_TYPE, "");
    }

    public void clearSnsType() {
        Prefs.remove(PrefsKey.KEY_SNS_TYPE);
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
