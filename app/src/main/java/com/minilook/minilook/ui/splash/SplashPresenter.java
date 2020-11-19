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

    void onUpdateDialogOkClick();

    void onUpdateDialogCancelClick();

    void onErrorDialogOkClick();

    void onDynamicLinkCheckComplete(Task<PendingDynamicLinkData> task);

    interface View {

        void setupLottieView();

        void showUpdateDialog();

        void showErrorDialog();

        void checkDynamicLink();

        void navigateToPlayStore();

        void navigateToGuide();

        void navigateToMain();

        void finish();
    }
}
