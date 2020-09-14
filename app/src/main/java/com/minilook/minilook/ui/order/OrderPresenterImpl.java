package com.minilook.minilook.ui.order;

import com.google.gson.Gson;
import com.minilook.minilook.App;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.model.order.OrderSheetDataModel;
import com.minilook.minilook.data.model.shipping.IslandDataModel;
import com.minilook.minilook.data.model.shipping.ShippingDataModel;
import com.minilook.minilook.data.model.shopping.ShoppingBrandDataModel;
import com.minilook.minilook.data.model.shopping.ShoppingOptionDataModel;
import com.minilook.minilook.data.model.shopping.ShoppingProductDataModel;
import com.minilook.minilook.data.model.user.CouponDataModel;
import com.minilook.minilook.data.model.user.PointDataModel;
import com.minilook.minilook.data.network.order.OrderRequest;
import com.minilook.minilook.data.network.shipping.ShippingRequest;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.data.type.ShippingCode;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.order.di.OrderArguments;
import java.util.List;
import kr.co.bootpay.enums.Method;
import lombok.AllArgsConstructor;
import lombok.Getter;
import timber.log.Timber;

public class OrderPresenterImpl extends BasePresenterImpl implements OrderPresenter {

    private final View view;
    private final List<ShoppingBrandDataModel> orderItem;
    private final BaseAdapterDataModel<ShoppingBrandDataModel> orderAdapter;
    private final BaseAdapterDataModel<CouponDataModel> couponAdapter;
    private final OrderRequest orderRequest;
    private final ShippingRequest shippingRequest;

    private Gson gson = new Gson();

    private int availableCouponCount = -1;
    private boolean isCouponBoxOpen = false;
    private int havePoint;

    private int totalProductPrice = 0;
    private int totalProductCount = 0;
    private int totalShippingPrice = 0;
    private int applyCouponPrice = 0;
    private CouponDataModel selectedCoupon;
    private int inputPoint = 0;
    private int selectedPoint = 0;
    private int totalPointEarned = 0;

    private Method payment = Method.BANK;

    private boolean isSelectedShipping = false;
    private boolean isCheckOrderInfo = false;

    public OrderPresenterImpl(OrderArguments args) {
        view = args.getView();
        orderItem = App.getInstance().getOrderItems();
        App.getInstance().getOrderItems().clear();
        orderAdapter = args.getOrderAdapter();
        couponAdapter = args.getCouponAdapter();
        orderRequest = new OrderRequest();
        shippingRequest = new ShippingRequest();
    }

    @Override public void onCreate() {
        toRxObservable();
        view.setupProductRecyclerView();
        view.setupCouponRecyclerView();
        view.setupPointEditText();

        calPrice();
        reqOrder();
    }

    @Override public void onShippingClick() {
        view.navigateToShipping();
    }

    @Override public void onCouponBoxClick() {
        handleCouponBox();
    }

    @Override public void onPointAllUseClick() {
        selectedPoint = Math.min(applyCouponPrice, havePoint);
        view.setupPoint(selectedPoint);
        setupTotalPrice();
    }

    @Override public void onPointEditTextChanged(int point) {
        if (point > havePoint) {
            view.showOverPointToast();
            view.setupPoint(inputPoint);
            return;
        } else if (point > applyCouponPrice) {
            view.showOverTotalPriceToast();
            view.setupPoint(inputPoint);
            return;
        }
        inputPoint = point;
    }

    @Override public void onPointEditTextEnter() {
        if (inputPoint < 1000) {
            inputPoint = 0;
            view.showUseMinPointToast();
        }
        selectedPoint = inputPoint;
        view.setupPoint(selectedPoint);
        setupTotalPrice();
    }

    @Override public void onPaymentCardClick() {
        payment = Method.CARD;
        view.selectedCard();
    }

    @Override public void onPaymentBankClick() {
        payment = Method.BANK;
        view.selectedBank();
    }

    @Override public void onOrderInfoCheck() {
        isCheckOrderInfo = !isCheckOrderInfo;
        if (isCheckOrderInfo) {
            view.checkOrderInfo();
        } else {
            view.uncheckOrderInfo();
        }
        checkOrderConfirmButton();
    }

    private void calPrice() {
        for (ShoppingBrandDataModel brandData : orderItem) {
            int totalProductsPrice = 0;
            int totalOptionCount = 0;
            for (ShoppingProductDataModel productData : brandData.getProducts()) {
                totalProductCount++;
                int price_basic = productData.getPrice();
                int pointEarnedPercent = productData.getPoint_percent();
                for (ShoppingOptionDataModel optionData : productData.getOptions()) {
                    int price = price_basic + optionData.getPrice_add();
                    optionData.setPrice_sum(price);
                    int quantity = optionData.getQuantity();
                    totalOptionCount += quantity;
                    int orderPrice = price * quantity;
                    totalProductsPrice += orderPrice;
                    int pointEarned = (int) (orderPrice * (pointEarnedPercent / 100f));
                    totalPointEarned += pointEarned;
                }
            }

            totalProductPrice += totalProductsPrice;
            brandData.setTotal_products_price(totalProductsPrice);
            brandData.setTotal_option_count(totalOptionCount);

            boolean isFreeShipping;
            int finalShippingPrice;
            int shippingCode = brandData.getShipping_type();
            if (shippingCode == ShippingCode.FREE.getValue()) {
                isFreeShipping = true;
                finalShippingPrice = 0;
            } else if (shippingCode == ShippingCode.CONDITIONAL.getValue()) {
                isFreeShipping = brandData.getTotal_products_price() >= brandData.getCondition_free_shipping();
                if (isFreeShipping) {
                    finalShippingPrice = 0;
                } else {
                    finalShippingPrice = brandData.getCondition_shipping_price();
                }
            } else {
                isFreeShipping = false;
                finalShippingPrice = brandData.getShipping_price();
            }

            brandData.setFreeShipping(isFreeShipping);
            brandData.setFinal_shipping_price(finalShippingPrice);
        }
        view.setupTotalProductPrice(totalProductPrice);
        view.setupConfirmPointEarned(totalPointEarned);
        view.setupReviewPointEarned(100 * totalProductCount);
        view.setupTotalPointEarned(totalPointEarned + (100 * totalProductCount));
    }

    private void reqOrder() {
        addDisposable(orderRequest.getOrderSheet()
            .compose(Transformer.applySchedulers())
            .filter(data -> data.getCode().equals(HttpCode.OK))
            .map(data -> gson.fromJson(data.getData(), OrderSheetDataModel.class))
            .subscribe(this::resOrderSheet, Timber::e));
    }

    private void resOrderSheet(OrderSheetDataModel data) {
        setupShippingData(data.getShipping());
        setupCouponData(data.getCoupons());
        setupPointData(data.getPoint());
    }

    private void setupShippingData(ShippingDataModel shippingData) {
        if (shippingData != null) {
            isSelectedShipping = true;
            view.setupName(shippingData.getName());
            view.setupPhone(shippingData.getPhone());
            view.setupAddress(shippingData.getZipcode(), shippingData.getAddress(), shippingData.getAddress_detail());

            if (shippingData.isDefault()) {
                view.showDefaultLabel();
            } else {
                view.hideDefaultLabel();
            }

            view.showShippingPanel();
            view.hideShippingAddPanel();

            reqCheckIsland(shippingData.getAddress_id());
        } else {
            isSelectedShipping = false;
            view.hideShippingPanel();
            view.showShippingAddPanel();
        }
        checkOrderConfirmButton();
    }

    private void setupCouponData(List<CouponDataModel> coupons) {
        if (coupons != null && coupons.size() > 0) {
            coupons.add(0, new CouponDataModel());
            for (CouponDataModel couponData : coupons) {
                if (couponData.getUse_condition() < totalProductPrice) {
                    availableCouponCount++;
                    couponData.setAvailable(true);
                } else {
                    couponData.setAvailable(false);
                }
            }
            couponAdapter.set(coupons);
            view.couponRefresh();
        }
    }

    private void setupPointData(PointDataModel point) {
        havePoint = point.getPoint();
        view.setupHavePoint(havePoint);
        if (havePoint == 0) view.disablePointBox();
    }

    private void reqCheckIsland(int address_id) {
        addDisposable(shippingRequest.checkIsland(address_id)
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                String code = data.getCode();
                if (code.equals(HttpCode.NO_DATA)) {
                    setupIslandData(false, 0);
                    initData();
                }
                return code.equals(HttpCode.OK);
            })
            .map(data -> gson.fromJson(data.getData(), IslandDataModel.class))
            .subscribe(this::resCheckIsland, Timber::e));
    }

    private void resCheckIsland(IslandDataModel data) {
        setupIslandData(true, data.getIsland_shipping_price());
        initData();
    }

    private void initData() {
        initCoupon();
        initPoint();
        setupTotalPrice();
    }

    private void setupIslandData(boolean isIsland, int islandShippingPrice) {
        totalShippingPrice = 0;
        for (ShoppingBrandDataModel brandData : orderItem) {
            brandData.setIsland(isIsland);
            brandData.setIsland_shipping_price(islandShippingPrice);
            totalShippingPrice += (brandData.getFinal_shipping_price() + islandShippingPrice);
        }
        view.setupTotalShippingPrice(totalShippingPrice);
        orderAdapter.set(orderItem);
        view.productRefresh();
    }

    private void initCoupon() {
        selectedCoupon = null;
        if (availableCouponCount == -1) {
            view.setupEmptyCouponBoxText();
            view.disableCouponBox();
        } else if (availableCouponCount == 0) {
            view.setupNoAvailableCouponBoxText();
            view.disableCouponBox();
        } else {
            view.setupCouponBoxText(availableCouponCount);
            view.enableCouponBox();
        }

        if (isCouponBoxOpen) handleCouponBox();
    }

    private void initPoint() {
        inputPoint = 0;
        selectedPoint = 0;
        view.setupPoint(selectedPoint);
    }

    private void setupTotalPrice() {
        int selectedCouponPrice = selectedCoupon != null ? selectedCoupon.getCoupon() : 0;
        view.setupTotalCoupon(selectedCouponPrice);
        view.setupPoint(selectedPoint);

        applyCouponPrice = totalProductPrice + totalShippingPrice - selectedCouponPrice;
        int totalPrice = applyCouponPrice - selectedPoint;
        view.setupTotalPrice(totalPrice);
    }

    private void handleCouponBox() {
        isCouponBoxOpen = !isCouponBoxOpen;
        if (isCouponBoxOpen) {
            view.openCouponBox();
            view.setupArrowUp();
            view.setupOpenCouponBoxText();
        } else {
            view.closeCouponBox();
            view.setupArrowDown();
            if (selectedCoupon != null) {
                view.setupSelectedCouponBoxText(selectedCoupon.getCoupon(), selectedCoupon.getName());
            } else {
                view.setupCouponBoxText(availableCouponCount);
            }
        }
    }

    private void checkOrderConfirmButton() {
        if (isSelectedShipping && isCheckOrderInfo) {
            view.enableOrderConfirmButton();
        } else {
            view.disableOrderConfirmButton();
        }
    }

    private void toRxObservable() {
        addDisposable(RxBus.toObservable().subscribe(o -> {
            if (o instanceof RxEventShippingSelected) {
                ShippingDataModel shippingData = ((RxEventShippingSelected) o).getShipping();
                setupShippingData(shippingData);
            } else if (o instanceof RxEventCouponSelected) {
                CouponDataModel couponData = ((RxEventCouponSelected) o).getCoupon();
                if (couponData.getCoupon_id() != -1) {
                    selectedCoupon = couponData;
                } else {
                    selectedCoupon = null;
                }
                handleCouponBox();
                initPoint();
                setupTotalPrice();
            }
        }, Timber::e));
    }

    @AllArgsConstructor @Getter public final static class RxEventShippingSelected {
        private ShippingDataModel shipping;
    }

    @AllArgsConstructor @Getter public final static class RxEventCouponSelected {
        private CouponDataModel coupon;
    }
}