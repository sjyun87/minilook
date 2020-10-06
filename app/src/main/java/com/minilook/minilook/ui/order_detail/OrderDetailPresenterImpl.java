package com.minilook.minilook.ui.order_detail;

import com.google.gson.Gson;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.order.OrderBrandDataModel;
import com.minilook.minilook.data.model.order.OrderCancelDataModel;
import com.minilook.minilook.data.model.order.OrderDataModel;
import com.minilook.minilook.data.model.order.OrderDetailDataModel;
import com.minilook.minilook.data.model.order.OrderProductDataModel;
import com.minilook.minilook.data.model.shipping.ShippingDataModel;
import com.minilook.minilook.data.network.order.OrderRequest;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.order_detail.di.OrderDetailArguments;
import com.minilook.minilook.ui.review_write.ReviewWritePresenterImpl;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import timber.log.Timber;

public class OrderDetailPresenterImpl extends BasePresenterImpl implements OrderDetailPresenter {

    private final View view;
    private final String orderNo;
    private final String receiptNo;
    private final BaseAdapterDataModel<OrderBrandDataModel> adapter;
    private final OrderRequest orderRequest;

    private Gson gson = new Gson();

    private OrderDataModel orderData;

    public OrderDetailPresenterImpl(OrderDetailArguments args) {
        view = args.getView();
        orderNo = args.getOrder_id();
        receiptNo = args.getReceipt_id();
        adapter = args.getAdapter();
        orderRequest = new OrderRequest();
    }

    @Override public void onCreate() {
        toRxObservable();
        view.setupRecyclerView();

        reqOrderDetail();
    }

    @Override public void onOrderAllCancelClick() {
        view.navigateToOrderCancel(getItems());
    }

    @Override public void onPurchaseConfirmDialogOkClick(int orderOptionNo) {
        reqPurchaseConfirm(orderOptionNo);
    }

    private void reqOrderDetail() {
        addDisposable(orderRequest.getOrderDetail(orderNo, receiptNo)
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                String code = data.getCode();
                return code.equals(HttpCode.OK);
            })
            .map(data -> gson.fromJson(data.getData(), OrderDetailDataModel.class))
            .subscribe(this::resOrderDetail, Timber::e));
    }

    private void resOrderDetail(OrderDetailDataModel data) {
        orderData = data.getOrderData();
        view.setOrderNo(orderData.getOrderNo());
        view.setOrderDate(orderData.getOrderDate());
        view.setPaymentMethod(orderData.getPaymentMethod());
        view.setPaymentPrice(orderData.getPaymentPrice());
        view.setProductPrice(orderData.getProductPrice());
        view.setShippingPrice(orderData.getShippingPrice());
        view.setCouponPrice(orderData.getCoupon());
        view.setPoint(orderData.getPoint());

        adapter.set(orderData.getBrands());
        view.refresh();

        ShippingDataModel shippingData = data.getShippingData();
        view.setShippingName(shippingData.getName());
        view.setShippingPhone(shippingData.getPhone());
        view.setShippingAddress(shippingData.getZipcode(), shippingData.getAddress(), shippingData.getAddressDetail());

        handleAllCancelButton();
    }

    private void handleAllCancelButton() {
        for (OrderBrandDataModel brandData : orderData.getBrands()) {
            for (OrderProductDataModel productData : brandData.getGoods()) {
                int statusCode = productData.getStatusCode();
                if (statusCode > 3) {
                    view.hideAllCancelButton();
                    return;
                }
            }
        }
    }

    private void reqPurchaseConfirm(int orderOptionNo) {
        addDisposable(orderRequest.setPurchaseConfirm(orderOptionNo)
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                String code = data.getCode();
                return code.equals(HttpCode.OK);
            })
            .subscribe(this::resPurchaseConfirm, Timber::e));
    }

    private void resPurchaseConfirm(BaseDataModel data) {
        reqOrderDetail();
    }

    private OrderCancelDataModel getItems() {
        OrderCancelDataModel cancelData = new OrderCancelDataModel();
        cancelData.setOrderNo(orderData.getOrderNo());
        cancelData.setOrderDate(orderData.getOrderDate());
        cancelData.setReceiptId(orderData.getReceiptId());
        ArrayList<OrderProductDataModel> items = new ArrayList<>();
        for (OrderBrandDataModel brandData : orderData.getBrands()) {
            cancelData.setBrandName(brandData.getBrandName());
            items.addAll(brandData.getGoods());
        }
        cancelData.setGoods(items);
        cancelData.setAllCancel(true);
        return cancelData;
    }

    private void toRxObservable() {
        addDisposable(RxBus.toObservable().subscribe(o -> {
            if (o instanceof RxBusEventOrderCancelClick) {
                OrderProductDataModel data = ((RxBusEventOrderCancelClick) o).getData();
                OrderCancelDataModel cancelData = new OrderCancelDataModel();
                cancelData.setOrderNo(orderData.getOrderNo());
                cancelData.setOrderDate(orderData.getOrderDate());
                cancelData.setReceiptId(orderData.getReceiptId());
                ArrayList<OrderProductDataModel> items = new ArrayList<>();
                items.add(data);
                cancelData.setGoods(items);
                cancelData.setAllCancel(false);
                view.navigateToOrderCancel(cancelData);
            } else if (o instanceof RxBusEventQuestionClick) {
                String csPhone = ((RxBusEventQuestionClick) o).getCsPhone();
                view.navigateToDial(csPhone);
            } else if (o instanceof RxBusEventExchangeNReturnClick) {
                OrderProductDataModel data = ((RxBusEventExchangeNReturnClick) o).getData();
                view.navigateToOrderExchangeNReturn(data);
            } else if (o instanceof RxBusEventDeliveryTrackingClick) {
                String trackingUrl = ((RxBusEventDeliveryTrackingClick) o).getTrackingUrl();
                view.navigateToOutlink(trackingUrl);
            } else if (o instanceof RxBusEventPurchaseConfirmClick) {
                int orderOptionNo = ((RxBusEventPurchaseConfirmClick) o).getOrderOptionNo();
                view.showPurchaseConfirmDialog(orderOptionNo);
            } else if (o instanceof RxBusEventWriteReviewClick) {
                OrderProductDataModel data = ((RxBusEventWriteReviewClick) o).getData();
                view.navigateToReviewWrite(receiptNo, data);
            } else if (o instanceof RxBusEventStatusRefresh) {
                reqOrderDetail();
            } else if (o instanceof RxBusEventCancelQuestionClick) {
                view.navigateToMinilookTalk();
            } else if (o instanceof ReviewWritePresenterImpl.RxEventReviewWrite) {
                reqOrderDetail();
            }
        }, Timber::e));
    }

    @AllArgsConstructor @Getter public final static class RxBusEventOrderCancelClick {
        private OrderProductDataModel data;
    }

    @AllArgsConstructor @Getter public final static class RxBusEventQuestionClick {
        private String csPhone;
    }

    @AllArgsConstructor @Getter public final static class RxBusEventExchangeNReturnClick {
        private OrderProductDataModel data;
    }

    @AllArgsConstructor @Getter public final static class RxBusEventDeliveryTrackingClick {
        private String trackingUrl;
    }

    @AllArgsConstructor @Getter public final static class RxBusEventPurchaseConfirmClick {
        private int orderOptionNo;
    }

    @AllArgsConstructor @Getter public final static class RxBusEventWriteReviewClick {
        private OrderProductDataModel data;
    }

    @AllArgsConstructor @Getter public final static class RxBusEventCancelQuestionClick {
    }

    @AllArgsConstructor @Getter public final static class RxBusEventStatusRefresh {
    }
}