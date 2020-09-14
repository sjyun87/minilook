package com.minilook.minilook.ui.order;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import com.minilook.minilook.data.model.bootpay.BootPayDataModel;

public interface OrderPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onShippingClick();

    void onMemoBoxClick();

    void onCouponBoxClick();

    void onPointAllUseClick();

    void onPointEditTextChanged(int point);

    void onPointEditTextEnter();

    void onPaymentCardClick();

    void onPaymentBankClick();

    void onOrderInfoCheck();

    void onOrderConfirmClick();

    void onBootPayConfirm(String orderId, String message);

    void onBootPayDone(BootPayDataModel bootPayData, String message);

    interface View {

        void setupMemoRecyclerView();

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

        void showMemoBox();

        void openMemoBox();

        void closeMemoBox();

        void setupOpenMemoBoxText();

        void setupDirectInputMemoBoxText();

        void setupMemoBoxText(String memo);

        void showDirectMemoEditText();

        void hideDirectMemoEditText();

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

        void showBootPay(BootPayDataModel bootPayData);

        void setBootPayConfirm(String message);

        void setBootPayCancel();

        void showOutOfStockDialog();
    }
}
