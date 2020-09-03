package com.minilook.minilook.ui.leave;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface LeavePresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onLeaveClick();

    interface View {

        void setupPoint(int point);

        void setupCoupon(int coupon);

        void setupChainSNS(String type);

        void navigateToMain();

        void finish();
    }
}
