package com.minilook.minilook.ui.order_detail;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import com.minilook.minilook.data.model.order.OrderCancelDataModel;
import com.minilook.minilook.data.model.order.OrderProductDataModel;

public interface OrderDetailPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onOrderAllCancelClick();

    void onPurchaseConfirmDialogOkClick(int orderOptionNo);

    void onBrandCallDialogOkClick(String csTel);

    interface View {

        void setupRecyclerView();

        void refresh();

        void setOrderNo(String no);

        void setOrderDate(String date);

        void hideAllCancelButton();

        void setShippingName(String name);

        void setShippingPhone(String phone);

        void setShippingAddress(String zipcode, String address, String address_detail);

        void setPaymentMethod(String method);

        void setPaymentPrice(int price);

        void setProductPrice(int price);

        void setShippingPrice(int price);

        void setCouponPrice(int price);

        void setPoint(int point);

        void showPurchaseConfirmDialog(int orderOptionNo);

        void showBrandCallDialog(String name, String logo, String csTime, String csTel);

        void navigateToOutlink(String url);

        void navigateToDial(String csPhone);

        void navigateToOrderExchangeNReturn(OrderProductDataModel data);

        void navigateToOrderCancel(OrderCancelDataModel items);

        void navigateToMinilookTalk();

        void navigateToReviewWrite(String orderNo, String orderDate, OrderProductDataModel data);

        void navigateToQuestionWrite(int productNo);
    }
}
