package com.minilook.minilook.ui.splash;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface SplashPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onAnimationEnd();

    void onUpdateDialogOkClick();

    void onUpdateDialogCancelClick();

    void onErrorDialogOkClick();

    interface View {

        void setupLottieView();

        void showUpdateDialog();

        void showErrorDialog();

        void navigateToPlayStore();

        void navigateToGuide();

        void navigateToMain();

        void finish();
    }
}
