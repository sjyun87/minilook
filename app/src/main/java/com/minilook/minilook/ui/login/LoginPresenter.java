package com.minilook.minilook.ui.login;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import com.minilook.minilook.data.model.user.UserDataModel;

public interface LoginPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onNaverClick();

    void onKakaoClick();

    void onLoginSuccess(String id, String email, String type);

    void onLoginError(int errorCode, String message);

    void onCloseClick();

    interface View {

        void setupKakaoLoginManager();

        void setupNaverLoginManager();

        void setupNoEmailDialog();

        void showNoEmailDialog();

        void finish();

        void navigateToJoin(UserDataModel userData);
    }
}
