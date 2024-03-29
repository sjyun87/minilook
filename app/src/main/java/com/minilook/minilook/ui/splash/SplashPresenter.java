package com.minilook.minilook.ui.splash;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import com.google.android.gms.tasks.Task;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;

public interface SplashPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onAnimationEnd();

    void onDynamicLink(Task<PendingDynamicLinkData> task);

    void onPushToken(Task<String> task);

    void onUpdateDialogOkClick();

    interface View {

        void setupLottieView();

        void setupDynamicLink();

        void setupPushToken();

        void showUpdateDialog();

        void showErrorDialog();

        void navigateToPlayStore();

        void navigateToGuide();

        void navigateToMain();

        void finish();
    }
}
