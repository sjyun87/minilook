package com.minilook.minilook.ui.login.kakao;

import android.content.Context;
import com.kakao.sdk.auth.LoginClient;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import timber.log.Timber;

public class KakaoLoginManager {

    private Context context;
    private LoginClient client;

    public KakaoLoginManager(Context context) {
        this.context = context;
        this.client = LoginClient.getInstance();
    }

    public void login() {
        if (client.isKakaoTalkLoginAvailable(context)) {
            client.loginWithKakaoTalk(context, (oAuthToken, error) -> {
                if (error != null) {
                    Timber.e("Kakao Login :: error = %s", error.getMessage());
                } else {
                    Timber.e("Kakao Login :: Access Token :: %s", oAuthToken.getAccessToken());
                }
                return null;
            });
        } else {
            client.loginWithKakaoAccount(context, (oAuthToken, error) -> {
                if (error != null) {
                    Timber.e("Kakao Login :: error = %s", error.getMessage());
                } else {
                    Timber.e("Kakao Login :: Access Token :: %s", oAuthToken.getAccessToken());
                }
                return null;
            });
        }
    }

    public void logout() {
        UserApiClient.getInstance().logout(error -> {
            if (error != null) {
                Timber.e("Kakao Logout :: error = %s", error.getMessage());
            } else {
                Timber.e("Kakao Logout :: Success!");
            }
            return null;
        });
    }
}
