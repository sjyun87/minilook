package com.minilook.minilook.ui.login.naver;

import android.annotation.SuppressLint;
import android.app.Activity;
//import android.content.Context;
//import androidx.core.content.ContextCompat;
//import com.minilook.minilook.R;
//import com.nhn.android.naverlogin.OAuthLogin;
//import com.nhn.android.naverlogin.OAuthLoginHandler;
//import timber.log.Timber;

public class NaverLoginManager {

    //private Activity activity;
    //private OAuthLogin oAuthLogin;

    public NaverLoginManager(Activity activity) {
        //this.activity = activity;
        //this.oAuthLogin = OAuthLogin.getInstance();
        //init();
    }

    private void init() {
        //oAuthLogin.init(
        //    activity,
        //    activity.getString(R.string.naver_client_id),
        //    activity.getString(R.string.naver_client_secret),
        //    activity.getString(R.string.naver_client_app_name)
        //);
    }

    @SuppressLint("HandlerLeak")
    public void login() {
        //oAuthLogin.startOauthLoginActivity(activity, new OAuthLoginHandler() {
        //    @Override public void run(boolean success) {
        //        if (success) {
        //            Timber.e("Naver Login :: Access Token :: %s", oAuthLogin.getAccessToken(activity));
        //        } else {
        //            Timber.e("Naver Login :: error code = %s / error message = %s",
        //                oAuthLogin.getLastErrorCode(activity).getCode(), oAuthLogin.getLastErrorDesc(activity));
        //        }
        //    }
        //});
    }

    public void logout() {
        //oAuthLogin.logout(activity);
    }
}
