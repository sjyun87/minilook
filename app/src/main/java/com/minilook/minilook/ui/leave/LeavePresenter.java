package com.minilook.minilook.ui.leave;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface LeavePresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onLeaveClick();

    void onLeaveDialogOkClick();

    interface View {

        void setupPoint(int point);

        void setupCoupon(int coupon);

        void setupChainSNS(String type);

        void showLeaveDialog();

        void navigateToMain();

        void finish();
    }
}
