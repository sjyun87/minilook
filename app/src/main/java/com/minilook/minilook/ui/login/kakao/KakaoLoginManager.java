package com.minilook.minilook.ui.login.kakao;

import android.content.Context;
import com.kakao.sdk.auth.LoginClient;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.minilook.minilook.data.type.LoginType;
import com.minilook.minilook.ui.login.listener.OnLoginListener;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import lombok.Setter;
import timber.log.Timber;

public class KakaoLoginManager {

    public static final int ERROR_LOGIN = 0;
    public static final int ERROR_NO_EMAIL = 1;
    public static final int ERROR_LOGOUT = 2;

    private Context context;
    private LoginClient client;
    @Setter private OnLoginListener listener;

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
                Timber.e("Kakao Login :: error = %s", error.getMessage());
                if (listener != null) listener.onError(ERROR_LOGIN, error.getMessage());
            } else {
                Timber.e("Kakao Login :: Access Token :: %s", oAuthToken.getAccessToken());
                getUserData(oAuthToken.getAccessToken());
            }
            return null;
        }
    };

    private void getUserData(String accessToken) {
        UserApiClient.getInstance().me((user, error) -> {
            if (error != null) {
                Timber.e("Kakao Login :: error = %s", error.getMessage());
                if (listener != null) listener.onError(ERROR_LOGIN, error.getMessage());
            } else if (user != null && user.getKakaoAccount() != null) {
                String id = String.valueOf(user.getId());
                String email = user.getKakaoAccount().getEmail();
                if (email != null) {
                    if (listener != null) listener.onLogin(id, email, LoginType.KAKAO.getValue());
                } else {
                    if (listener != null) listener.onError(ERROR_NO_EMAIL, "No Email..");
                }
            }
            return null;
        });
    }

    public void logout() {
        UserApiClient.getInstance().logout(error -> {
            if (error != null) {
                Timber.e("Kakao Logout :: error = %s", error.getMessage());
                if (listener != null) listener.onError(ERROR_LOGOUT, error.getMessage());
            } else {
                Timber.e("Kakao Logout :: Success!");
                if (listener != null) listener.onLogout();
            }
            return null;
        });
    }
}
