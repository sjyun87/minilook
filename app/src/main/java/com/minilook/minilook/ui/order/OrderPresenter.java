package com.minilook.minilook.ui.order;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface OrderPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onShippingClick();

    void onCouponBoxClick();

    void onPointAllUseClick();

    void onPointEditTextChanged(int point);

    void onPointEditTextEnter();

    void onPaymentCardClick();

    void onPaymentBankClick();

    void onOrderInfoCheck();

    interface View {

        void showShippingPanel();

        void hideShippingPanel();

        void showShippingAddPanel();

        void hideShippingAddPanel();

        void setupName(String name);

        void setupPhone(String phone);

        void setupAddress(String zipcode, String address, String address_detail);

        void showDefaultLabel();

        void hideDefaultLabel();

        void navigateToShipping();

        void setupProductRecyclerView();

        void productRefresh();

        void setupTotalPrice(int price);

        void setupTotalShippingPrice(int price);

        void setupTotalProductPrice(int price);

        void setupCouponBoxText(int count);

        void setupSelectedCouponBoxText(int coupon, String name);

        void setupEmptyCouponBoxText();

        void setupNoAvailableCouponBoxText();

        void setupOpenCouponBoxText();

        void enableCouponBox();

        void disableCouponBox();

        void setupCouponRecyclerView();

        void couponRefresh();

        void openCouponBox();

        void closeCouponBox();

        void setupArrowUp();

        void setupArrowDown();

        void setupTotalCoupon(int coupon);

        void setupPointEditText();

        void setupHavePoint(int point);

        void disablePointBox();

        void setupPoint(int point);

        void showOverPointToast();

        void showOverTotalPriceToast();

        void showUseMinPointToast();

        void selectedCard();

        void selectedBank();

        void setupConfirmPointEarned(int point);

        void setupReviewPointEarned(int point);

        void setupTotalPointEarned(int point);

        void checkOrderInfo();

        void uncheckOrderInfo();

        void enableOrderConfirmButton();

        void disableOrderConfirmButton();
    }
}
