package com.minilook.minilook.ui.product_detail;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface ProductDetailPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    interface View {

        void setupProductImageViewPager();

        void productImageRefresh();

        void setupBrandName(String text);

        void setupProductName(String text);

        void setupPriceOrigin(String text);

        void showPriceOrigin();

        void hidePriceOrigin();

        void setupDiscountPercent(int percent);

        void showDiscountPercent();

        void hideDiscountPercent();

        void setupPrice(String text);

        void setupPoint(int point);

        void setupDeliveryInfoTextView();
    }
}
