package com.minilook.minilook.ui.splash;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface SplashPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onUpdateDialogOkClick();

    void onUpdateDialogCancelClick();

    void onPermissionGranted();

    void onAnimationEnd();

    interface View {

        void setupLottieView();

        void checkPermission();

        void showUpdateDialog();

        void navigateToPlatStore();

        void navigateToGuide();

        void navigateToMain();

        void finish();
    }
}
