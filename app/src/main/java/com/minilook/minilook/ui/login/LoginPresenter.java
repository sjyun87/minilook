package com.minilook.minilook.ui.login;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface LoginPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onNaverClick();

    void onKakaoClick();

    interface View {

        void showNoEmailDialog();

        void finish();
    }
}
