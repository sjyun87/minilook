package com.minilook.minilook.ui.login.kakao;

import android.content.Context;
import android.text.TextUtils;
import com.kakao.sdk.auth.LoginClient;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.minilook.minilook.data.code.LoginType;
import com.minilook.minilook.ui.login.listener.OnSNSLoginListener;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import lombok.Setter;

public class KakaoLoginManager {

    public static final int ERROR_LOGIN = 0;
    public static final int ERROR_NO_EMAIL = 1;
    public static final int ERROR_LOGOUT = 2;

    private Context context;
    private LoginClient client;
    @Setter private OnSNSLoginListener listener;

    public KakaoLoginManager(Context context) {
        this.context = context;
        this.client = LoginClient.getInstance();
    }

    public void login() {
        if (client.isKakaoTalkLoginAvailable(context)) {
            client.loginWithKakaoTalk(context, callback);
        } else {
            client.loginWithKakaoAccount(context, callback);
        }
    }

    private Function2<OAuthToken, Throwable, Unit> callback = new Function2<OAuthToken, Throwable, Unit>() {
        @Override public Unit invoke(OAuthToken oAuthToken, Throwable error) {
            if (error != null) {
                // TODO 카카오톡은 깔려있으나, 연동이 되지 않은경우 예외 처리 필요
                if (listener != null) listener.onSNSError(ERROR_LOGIN, error.getMessage());
            } else {
                getUserData(oAuthToken.getAccessToken());
            }
            return null;
        }
    };

    private void getUserData(String accessToken) {
        UserApiClient.getInstance().me((user, error) -> {
            if (error != null) {
                if (listener != null) listener.onSNSError(ERROR_LOGIN, error.getMessage());
            } else if (user != null && user.getKakaoAccount() != null) {
                String id = String.valueOf(user.getId());
                String email = user.getKakaoAccount().getEmail();
                if (!TextUtils.isEmpty(email)) {
                    if (listener != null) listener.onSNSLogin(id, email, LoginType.KAKAO.getValue());
                } else {
                    if (listener != null) listener.onSNSError(ERROR_NO_EMAIL, "No Email..");
                }
            }
            return null;
        });
    }

    public void logout() {
        UserApiClient.getInstance().logout(error -> {
            if (error != null) {
                if (listener != null) listener.onSNSError(ERROR_LOGOUT, error.getMessage());
            } else {
                if (listener != null) listener.onSNSLogout();
            }
            return null;
        });
    }
}
