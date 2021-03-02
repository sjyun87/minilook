package com.minilook.minilook.ui.coupon;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface CouponPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onCouponInfoClick();

    void onRegistCouponCodeClick();

    void onRegistQRCodeClick();

    void onQRScan(String couponCode);

    interface View {

        void setupRecyclerView();

        void refresh();

        void emptyPanel();

        void navigateToWebView(String url);

        void showQRCodeScanner();

        void showRegistCouponDialog();

        void showDuplicateMessage();

        void showInvalidMessage();

        void showRegistCompleteMessage();
    }
}
