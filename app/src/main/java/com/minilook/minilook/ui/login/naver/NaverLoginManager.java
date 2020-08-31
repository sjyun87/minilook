package com.minilook.minilook.ui.login.naver;

import android.annotation.SuppressLint;
import android.app.Activity;
import androidx.core.content.ContextCompat;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.minilook.minilook.R;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.data.OAuthLoginState;
import lombok.Setter;
import timber.log.Timber;

public class NaverLoginManager {

    private static final String URL_REQUEST_ME = "https://openapi.naver.com/v1/nid/me";

    public static final int ERROR_LOGIN = 0;
    public static final int ERROR_NO_EMAIL = 1;
    public static final int ERROR_LOGOUT = 2;

    private Activity activity;
    private OAuthLogin oAuthLogin;
    private Gson gson = new Gson();
    @Setter private OnNaverLoginListener onNaverLoginListener;

    public NaverLoginManager(Activity activity) {
        this.activity = activity;
        this.oAuthLogin = OAuthLogin.getInstance();
    }

    private void init() {
        oAuthLogin.init(
            activity,
            activity.getResources().getString(R.string.naver_client_id),
            activity.getResources().getString(R.string.naver_client_secret),
            activity.getResources().getString(R.string.naver_client_app_name)
        );
    }

    @SuppressLint("HandlerLeak")
    public void login() {
        if (oAuthLogin.getState(activity).equals(OAuthLoginState.NEED_INIT)) {
            init();
        }

        oAuthLogin.startOauthLoginActivity(activity, new OAuthLoginHandler() {
            @Override public void run(boolean success) {
                if (success) {
                    Timber.e("Naver Login :: Access Token :: %s", oAuthLogin.getAccessToken(activity));
                    getUserData(oAuthLogin.getAccessToken(activity));
                } else {
                    Timber.e("Naver Login :: error code = %s / error message = %s",
                        oAuthLogin.getLastErrorCode(activity).getCode(), oAuthLogin.getLastErrorDesc(activity));
                    if (onNaverLoginListener != null) {
                        onNaverLoginListener.onNaverError(ERROR_LOGIN, oAuthLogin.getLastErrorDesc(activity));
                    }
                }
            }
        });
    }

    private void getUserData(String accessToken) {
        new Thread() {
            @Override public void run() {
                String data = oAuthLogin.requestApi(activity, accessToken, URL_REQUEST_ME);
                JsonObject json = gson.fromJson(data, JsonObject.class);
                String message = json.get("message").getAsString();
                JsonObject response = json.getAsJsonObject("response");
                String email = response.get("email").getAsString();
                if (email != null) {
                    if (onNaverLoginListener != null) onNaverLoginListener.onNaverLoginSuccess(email);
                } else {
                    if (onNaverLoginListener != null) onNaverLoginListener.onNaverError(ERROR_NO_EMAIL, message);
                }
            }
        }.start();
    }

    public void logout() {
        oAuthLogin.logout(activity);
    }

    public interface OnNaverLoginListener {
        void onNaverLoginSuccess(String email);

        void onNaverLogoutSuccess();

        void onNaverError(int errorCode, String message);
    }
}
