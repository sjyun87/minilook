package com.minilook.minilook.ui.login.kakao;

import android.content.Context;
import com.kakao.sdk.auth.LoginClient;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;
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
    @Setter private OnKakaoLoginListener onKakaoLoginListener;

    public KakaoLoginManager(Context context) {
        this.context = context;
        this.client = LoginClient.getInstance();
    }

    public void login() {
        if (client.isKakaoTalkLoginAvailable(context)) {
            client.loginWithKakaoTalk(context, (oAuthToken, error) -> {
                if (error != null) {
                    Timber.e("Kakao Login :: error = %s", error.getMessage());
                    if (onKakaoLoginListener != null) {
                        onKakaoLoginListener.onKakaoError(ERROR_LOGIN, error.getMessage());
                    }
                } else {
                    Timber.e("Kakao Login :: Access Token :: %s", oAuthToken.getAccessToken());
                    getUserData(oAuthToken.getAccessToken());
                }
                return null;
            });
        } else {
            client.loginWithKakaoAccount(context, (oAuthToken, error) -> {
                if (error != null) {
                    Timber.e("Kakao Login :: error = %s", error.getMessage());
                    if (onKakaoLoginListener != null) {
                        onKakaoLoginListener.onKakaoError(ERROR_LOGIN, error.getMessage());
                    }
                } else {
                    Timber.e("Kakao Login :: Access Token :: %s", oAuthToken.getAccessToken());
                    getUserData(oAuthToken.getAccessToken());
                }
                return null;
            });
        }
    }

    private void getUserData(String accessToken) {
        UserApiClient.getInstance().me((user, error) -> {
            if (error != null) {
                Timber.e("Kakao Login :: error = %s", error.getMessage());
                if (onKakaoLoginListener != null) {
                    onKakaoLoginListener.onKakaoError(ERROR_LOGIN, error.getMessage());
                }
            } else if (user != null && user.getKakaoAccount() != null) {
                String email = user.getKakaoAccount().getEmail();
                if (email != null) {
                    if (onKakaoLoginListener != null) onKakaoLoginListener.onKakaoLoginSuccess(email);
                } else {
                    if (onKakaoLoginListener != null) {
                        onKakaoLoginListener.onKakaoError(ERROR_NO_EMAIL, "No Email..");
                    }
                }
            }
            return null;
        });
    }

    public void logout() {
        UserApiClient.getInstance().logout(error -> {
            if (error != null) {
                Timber.e("Kakao Logout :: error = %s", error.getMessage());
                if (onKakaoLoginListener != null) onKakaoLoginListener.onKakaoError(ERROR_LOGOUT, error.getMessage());
            } else {
                Timber.e("Kakao Logout :: Success!");
                if (onKakaoLoginListener != null) onKakaoLoginListener.onKakaoLogoutSuccess();
            }
            return null;
        });
    }

    public interface OnKakaoLoginListener {
        void onKakaoLoginSuccess(String email);

        void onKakaoLogoutSuccess();

        void onKakaoError(int errorCode, String message);
    }
}
