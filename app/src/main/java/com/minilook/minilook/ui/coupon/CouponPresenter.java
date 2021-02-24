package com.minilook.minilook.ui.coupon;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface CouponPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onCouponInfoClick();

    void onCouponRegistClick();

    interface View {

        void setupRecyclerView();

        void refresh();

        void emptyPanel();

        void navigateToWebView(String url);
    }
}
