package com.minilook.minilook.ui.order;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.minilook.minilook.App;
import com.minilook.minilook.data.code.ShippingCode;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.bootpay.BootPayDataModel;
import com.minilook.minilook.data.model.bootpay.BootPayItemDataModel;
import com.minilook.minilook.data.model.brand.BrandShippingDataModel;
import com.minilook.minilook.data.model.member.CouponDataModel;
import com.minilook.minilook.data.model.member.MemberDataModel;
import com.minilook.minilook.data.model.member.PointHistoryDataModel;
import com.minilook.minilook.data.model.order.OrderCompleteDataModel;
import com.minilook.minilook.data.model.order.OrderCompleteOptionDataModel;
import com.minilook.minilook.data.model.order.OrderSheetDataModel;
import com.minilook.minilook.data.model.shipping.IslandDataModel;
import com.minilook.minilook.data.model.shipping.ShippingDataModel;
import com.minilook.minilook.data.model.shopping.ShoppingBrandDataModel;
import com.minilook.minilook.data.model.shopping.ShoppingOptionDataModel;
import com.minilook.minilook.data.model.shopping.ShoppingProductDataModel;
import com.minilook.minilook.data.network.order.OrderRequest;
import com.minilook.minilook.data.network.shipping.ShippingRequest;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.ipage.IpagePresenterImpl;
import com.minilook.minilook.ui.order.di.OrderArguments;
import com.minilook.minilook.ui.shipping.ShippingPresenterImpl;
import com.minilook.minilook.util.TrackingUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import kr.co.bootpay.enums.Method;
import kr.co.bootpay.model.BootExtra;
import kr.co.bootpay.model.BootUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import timber.log.Timber;

public class OrderPresenterImpl extends BasePresenterImpl implements OrderPresenter {

    private final View view;
    private final boolean isDirect;
    private final List<ShoppingBrandDataModel> orderItem;
    private final BaseAdapterDataModel<ShoppingBrandDataModel> orderAdapter;
    private final BaseAdapterDataModel<CouponDataModel> couponAdapter;
    private final OrderRequest orderRequest;
    private final ShippingRequest shippingRequest;

    private Gson gson = new Gson();

    private int availableCouponCount = -1;
    private boolean isMemoBoxOpen = false;
    private boolean isCouponBoxOpen = false;
    private int havePoint;

    private String selectedMemo = "";
    private int totalPrice = 0;
    private int totalProductPrice = 0;
    private int totalProductCount = 0;
    private int totalOptionCount = 0;
    private int totalShippingPrice = 0;
    private int applyCouponPrice = 0;
    private CouponDataModel selectedCoupon;
    private int inputPoint = 0;
    private int selectedPoint = 0;
    private int totalPointEarned = 0;

    private Method payment = Method.CARD;
    private MemberDataModel userData;
    private ShippingDataModel selectedShippingData;

    private boolean isSelectedShipping = false;
    private boolean isCheckOrderInfo = false;

    private String orderId;

    public OrderPresenterImpl(OrderArguments args) {
        view = args.getView();
        isDirect = args.isDirect();
        orderItem = App.getInstance().getOrderItems();
        App.getInstance().getOrderItems().clear();
        orderAdapter = args.getOrderAdapter();
        couponAdapter = args.getCouponAdapter();
        orderRequest = new OrderRequest();
        shippingRequest = new ShippingRequest();
    }

    @Override public void onCreate() {
        toRxObservable();
        view.setupMemoRecyclerView();
        view.setupProductRecyclerView();
        view.setupCouponRecyclerView();
        view.setupPointEditText();

        calPrice();
        reqOrderSheet();
    }

    @Override public void onResume() {
        TrackingUtil.pageTracking("주문서 페이지", OrderActivity.class.getSimpleName());
    }

    @Override public void onShippingClick() {
        view.navigateToShipping();
    }

    @Override public void onMemoBoxClick() {
        handleMemoBox();
    }

    @Override public void onCouponBoxClick() {
        handleCouponBox();
    }

    @Override public void onPointAllUseClick() {
        if (havePoint >= 1000) {
            selectedPoint = Math.min(applyCouponPrice, havePoint);
            view.setupPoint(selectedPoint);
            setupTotalPrice();
        } else {
            view.showUseMinPointToast();
        }
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

    @Override public void onOrderConfirmClick() {
        reqSafetyStock(getOrderId());
    }

    @Override public void onBootPayDone(BootPayDataModel bootPayData, String message) {
        JsonObject data = gson.fromJson(message, JsonObject.class);
        String receipt_id = data.get("receipt_id").getAsString();
        reqOrderComplete(receipt_id, bootPayData);
    }

    @Override public void onBootPayError(String message) {
        view.showBootPayErrorToast();
    }

    private void calPrice() {
        for (ShoppingBrandDataModel brandData : orderItem) {
            int totalProductsPrice = 0;
            int brandOptionCount = 0;
            for (ShoppingProductDataModel productData : brandData.getProducts()) {
                totalProductCount++;
                int price_basic = productData.getPrice();
                int pointEarnedPercent = productData.getAddPointPercent();
                for (ShoppingOptionDataModel optionData : productData.getOptions()) {
                    int price = price_basic + optionData.getPriceAdd();
                    optionData.setPriceSum(price);
                    int quantity = optionData.getQuantity();
                    brandOptionCount += quantity;
                    int orderPrice = price * quantity;
                    totalProductsPrice += orderPrice;
                    int pointEarned = (int) (orderPrice * (pointEarnedPercent / 100f));
                    totalPointEarned += pointEarned;
                }
            }

            totalProductPrice += totalProductsPrice;
            brandData.setTotalProductsPrice(totalProductsPrice);
            totalOptionCount += brandOptionCount;
            brandData.setTotalOptionCount(brandOptionCount);

            boolean isFreeShipping;
            int finalShippingPrice;
            int shippingCode = brandData.getShippingType();
            if (shippingCode == ShippingCode.FREE.getValue()) {
                isFreeShipping = true;
                finalShippingPrice = 0;
            } else if (shippingCode == ShippingCode.CONDITIONAL.getValue()) {
                isFreeShipping = brandData.getTotalProductsPrice() >= brandData.getConditionFreeShipping();
                if (isFreeShipping) {
                    finalShippingPrice = 0;
                } else {
                    finalShippingPrice = brandData.getConditionShippingPrice();
                }
            } else {
                isFreeShipping = false;
                finalShippingPrice = brandData.getShippingPrice();
            }

            brandData.setFreeShipping(isFreeShipping);
            brandData.setFinalShippingPrice(finalShippingPrice);
        }
        view.setupTotalProductPrice(totalProductPrice);
        view.setupConfirmPointEarned(totalPointEarned);
        view.setupReviewPointEarned(100 * totalProductCount);
        view.setupTotalPointEarned(totalPointEarned + (100 * totalProductCount));
    }

    private void reqOrderSheet() {
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
        userData = data.getUser();
    }

    private void setupShippingData(ShippingDataModel shippingData) {
        if (shippingData != null) {
            isSelectedShipping = true;
            selectedShippingData = shippingData;
            view.setupName(shippingData.getName());
            view.setupPhone(shippingData.getPhone());
            view.setupAddress(shippingData.getZipcode(), shippingData.getAddress(), shippingData.getAddressDetail());

            if (shippingData.isDefault()) {
                view.showDefaultLabel();
            } else {
                view.hideDefaultLabel();
            }

            view.showShippingPanel();
            view.hideShippingAddPanel();

            view.showMemoBox();

            reqCheckIsland(shippingData.getAddressNo());
        } else {
            isSelectedShipping = false;
            view.hideShippingPanel();
            view.showShippingAddPanel();
        }
        checkOrderConfirmButton();
    }

    private void setupCouponData(List<CouponDataModel> coupons) {
        availableCouponCount = -1;
        if (coupons != null && coupons.size() > 0) {
            coupons.add(0, new CouponDataModel());
            for (CouponDataModel couponData : coupons) {
                if (couponData.getCondition() < totalProductPrice) {
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

    private void setupPointData(PointHistoryDataModel point) {
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
        setupIslandData(true, data.getIslandShippingPrice());
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
            brandData.setIslandShippingPrice(islandShippingPrice);
            totalShippingPrice += (brandData.getFinalShippingPrice() + islandShippingPrice);
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
        int selectedCouponPrice = selectedCoupon != null ? selectedCoupon.getValue() : 0;
        view.setupTotalCoupon(selectedCouponPrice);
        view.setupPoint(selectedPoint);

        applyCouponPrice = totalProductPrice - selectedCouponPrice;
        totalPrice = applyCouponPrice - selectedPoint + totalShippingPrice;
        view.setupTotalPrice(totalPrice);
    }

    private void handleMemoBox() {
        isMemoBoxOpen = !isMemoBoxOpen;
        if (isMemoBoxOpen) {
            view.openMemoBox();
            view.setupOpenMemoBoxText();
        } else {
            view.closeMemoBox();
            if (selectedMemo.isEmpty()) {
                view.setupDirectInputMemoBoxText();
            } else {
                view.setupMemoBoxText(selectedMemo);
            }
        }
    }

    private void handleCouponBox() {
        isCouponBoxOpen = !isCouponBoxOpen;
        if (isCouponBoxOpen) {
            view.openCouponBox();
            view.setupOpenCouponBoxText();
        } else {
            view.closeCouponBox();
            if (selectedCoupon != null) {
                view.setupSelectedCouponBoxText(selectedCoupon.getValue(), selectedCoupon.getName());
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

    private void reqSafetyStock(String orderId) {
        addDisposable(orderRequest.setSafetyStock(orderId, orderItem)
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                Timber.e(data.toString());
                String code = data.getCode();
                if (code.equals(HttpCode.NO_STOCK)) {
                    view.setBootPayCancel();
                    view.showOutOfStockDialog();
                }
                return code.equals(HttpCode.OK);
            })
            .subscribe(this::resSafetyStock, Timber::e));
    }

    private void resSafetyStock(BaseDataModel dataModel) {
        reqBootPay();
    }

    private void reqOrderComplete(String receipt_id, BootPayDataModel bootPayData) {
        OrderCompleteDataModel orderCompleteDataModel = getOrderCompleteData(receipt_id, bootPayData);
        addDisposable(orderRequest.orderComplete(orderCompleteDataModel)
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                String code = data.getCode();
                if (!code.equals(HttpCode.OK)) {
                    view.showErrorToast();
                }
                return code.equals(HttpCode.OK);
            })
            .subscribe(this::resOrderComplete, Timber::e));
    }

    private void resOrderComplete(BaseDataModel dataModel) {
        RxBus.send(new IpagePresenterImpl.RxBusEventDataChanged());
        view.navigateToOrderComplete();
        view.finish();
    }

    private OrderCompleteDataModel getOrderCompleteData(String receipt_id, BootPayDataModel bootPayData) {
        OrderCompleteDataModel completeData = new OrderCompleteDataModel();
        completeData.setUser_id(App.getInstance().getMemberNo());
        completeData.setOrder_id(bootPayData.getOrderId());
        completeData.setPayment_price(bootPayData.getPrice());
        completeData.setUse_point_value(selectedPoint);
        int totalDiscount = 0;
        if (selectedCoupon != null) {
            completeData.setCoupon_id(selectedCoupon.getNo());
            completeData.setUse_coupon_value(selectedCoupon.getValue());
            totalDiscount += selectedCoupon.getValue();
        }
        totalDiscount += selectedPoint;
        completeData.setTotal_product_price(totalProductPrice);
        completeData.setTotal_discount_price(totalDiscount);
        completeData.setTotal_shipping_price(totalShippingPrice);
        completeData.setReceipt_id(receipt_id);
        completeData.setReceipt_name(selectedShippingData.getName());
        completeData.setReceipt_phone(selectedShippingData.getPhone());
        completeData.setAddress_id(selectedShippingData.getAddressNo());
        completeData.setZip(selectedShippingData.getZipcode());
        completeData.setAddress(selectedShippingData.getAddress());
        completeData.setAddress_detail(selectedShippingData.getAddressDetail());
        completeData.setShipping_memo(selectedMemo);
        List<BrandShippingDataModel> brandShippingData = new ArrayList<>();
        for (ShoppingBrandDataModel brandData : orderItem) {
            BrandShippingDataModel brandShippingModel = new BrandShippingDataModel();
            brandShippingModel.setBrandNo(brandData.getBrandNo());
            brandShippingModel.setShipping_price(
                brandData.getFinalShippingPrice() + brandData.getIslandShippingPrice());
            brandShippingData.add(brandShippingModel);
        }
        completeData.setBrand_shipping(brandShippingData);

        int totalCouponValue = completeData.getUse_coupon_value();
        int totalPointValue = completeData.getUse_point_value();
        int totalPerCouponValue = 0;
        int totalPerPointValue = 0;
        List<OrderCompleteOptionDataModel> completeOptionData = new ArrayList<>();
        for (int brandIndex = 0; brandIndex < orderItem.size(); brandIndex++) {
            ShoppingBrandDataModel brandData = orderItem.get(brandIndex);

            List<ShoppingProductDataModel> bonusProductData = new ArrayList<>();
            List<ShoppingProductDataModel> normalProductData = new ArrayList<>();

            for (int productIndex = 0; productIndex < brandData.getProducts().size(); productIndex++) {
                ShoppingProductDataModel targetProductData = brandData.getProducts().get(productIndex);
                if (targetProductData.isBonus()) {
                    bonusProductData.add(targetProductData);
                } else {
                    normalProductData.add(targetProductData);
                }
            }

            int totalBonusPrice = 0;
            for (ShoppingProductDataModel bonusProduct : bonusProductData) {
                for (ShoppingOptionDataModel optionData : bonusProduct.getOptions()) {
                    OrderCompleteOptionDataModel completeOptionModel = new OrderCompleteOptionDataModel();
                    completeOptionModel.setOption_id(optionData.getOptionNo());
                    completeOptionModel.setShoppingbag_id(optionData.getShoppingbagNo());

                    completeOptionModel.setPer_point_value(0);
                    completeOptionModel.setPer_coupon_value(0);
                    completeOptionModel.setPer_payment_price(optionData.getPriceSum());
                    completeOptionData.add(completeOptionModel);
                    totalBonusPrice += optionData.getPriceSum();
                }
            }

            for (int productIndex = 0; productIndex < normalProductData.size(); productIndex++) {
                ShoppingProductDataModel productData = normalProductData.get(productIndex);
                for (int optionIndex = 0; optionIndex < productData.getOptions().size(); optionIndex++) {
                    ShoppingOptionDataModel optionData = productData.getOptions().get(optionIndex);

                    float per = (float) optionData.getPriceSum() / (totalProductPrice - totalBonusPrice);
                    for (int optionQuantityIndex = 0; optionQuantityIndex < optionData.getQuantity();
                        optionQuantityIndex++) {
                        OrderCompleteOptionDataModel completeOptionModel = new OrderCompleteOptionDataModel();
                        completeOptionModel.setOption_id(optionData.getOptionNo());
                        completeOptionModel.setShoppingbag_id(optionData.getShoppingbagNo());

                        if (brandIndex == (orderItem.size() - 1)
                            && productIndex == (normalProductData.size() - 1)
                            && optionIndex == (productData.getOptions().size() - 1)
                            && optionQuantityIndex == (optionData.getQuantity() - 1)) {
                            int lastPerCouponValue = totalCouponValue - totalPerCouponValue;
                            int lastPerPointValue = totalPointValue - totalPerPointValue;
                            completeOptionModel.setPer_point_value(lastPerPointValue);
                            completeOptionModel.setPer_coupon_value(lastPerCouponValue);
                            completeOptionModel.setPer_payment_price(
                                optionData.getPriceSum() - lastPerCouponValue - lastPerPointValue);
                        } else {
                            int perCouponValue = (int) (totalCouponValue * per);
                            int perPointValue = (int) (totalPointValue * per);
                            totalPerCouponValue += perCouponValue;
                            totalPerPointValue += perPointValue;

                            completeOptionModel.setPer_point_value(perPointValue);
                            completeOptionModel.setPer_coupon_value(perCouponValue);
                            completeOptionModel.setPer_payment_price(
                                optionData.getPriceSum() - perCouponValue - perPointValue);
                        }
                        completeOptionData.add(completeOptionModel);
                    }
                }
            }
        }
        completeData.setComplete_option(completeOptionData);
        completeData.setDirectOrder(isDirect);
        return completeData;
    }

    private void reqBootPay() {
        BootPayDataModel bootPayData = new BootPayDataModel();
        bootPayData.setBootUser(getBootUser());
        bootPayData.setBootExtra(getBootExtra());
        bootPayData.setMethod(payment);
        bootPayData.setOrderId(orderId);
        bootPayData.setName(getProductName());
        bootPayData.setPrice(totalPrice);
        bootPayData.setItems(getItems());

        if (totalPrice == 0) {
            reqOrderComplete("", bootPayData);
        } else {
            view.showBootPay(bootPayData);
        }
    }

    private List<BootPayItemDataModel> getItems() {
        List<BootPayItemDataModel> bootPayItems = new ArrayList<>();
        for (ShoppingBrandDataModel brandData : orderItem) {
            for (ShoppingProductDataModel productData : brandData.getProducts()) {
                for (ShoppingOptionDataModel optionData : productData.getOptions()) {
                    BootPayItemDataModel bootPayModel = new BootPayItemDataModel();
                    bootPayModel.setId(String.valueOf(productData.getProductNo()));
                    bootPayModel.setName(productData.getProductName());
                    bootPayModel.setPrice(optionData.getPriceSum());
                    bootPayModel.setQuantity(optionData.getQuantity());
                    bootPayItems.add(bootPayModel);
                }
            }
        }
        return bootPayItems;
    }

    private String getProductName() {
        String productName = orderItem.get(0).getProducts().get(0).getProductName();
        if (totalOptionCount > 1) productName = productName + " 외 " + (totalOptionCount - 1) + "건";
        return productName;
    }

    @SuppressLint("SimpleDateFormat")
    private String getOrderId() {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmss");
        String orderTime = format.format(date);

        int sumBrandId = 0;
        int sumProductId = 0;
        for (ShoppingBrandDataModel brandData : orderItem) {
            sumBrandId += brandData.getBrandNo();
            for (ShoppingProductDataModel productData : brandData.getProducts()) {
                sumProductId += productData.getProductNo();
            }
        }

        String sumBrandNum = String.valueOf(sumBrandId);
        String orderBrandNum = sumBrandNum.substring(sumBrandNum.length() - 1);
        String sumProductNum = String.valueOf(sumProductId);
        String orderProductNum = sumProductNum.substring(sumProductNum.length() - 1);
        String userNum = String.valueOf(App.getInstance().getMemberNo());
        String orderUserNum = userNum.substring(userNum.length() - 1);
        String randomNum = String.valueOf((int) (Math.random() * 10));

        orderId = orderTime + orderBrandNum + orderProductNum + orderUserNum + randomNum;
        return orderId;
    }

    private BootUser getBootUser() {
        BootUser bootUser = new BootUser();
        if (userData.getName() != null && !TextUtils.isEmpty(userData.getName())) {
            bootUser.setUsername(userData.getName());
        } else {
            bootUser.setUsername("");
        }
        bootUser.setEmail(userData.getEmail());
        bootUser.setPhone(userData.getPhone());
        bootUser.setAddr("(" + selectedShippingData.getZipcode() + ") "
            + selectedShippingData.getAddress() + " "
            + selectedShippingData.getAddressDetail());
        return bootUser;
    }

    private BootExtra getBootExtra() {
        BootExtra bootExtra = new BootExtra();
        bootExtra.setQuotas(new int[] { 1, 2, 3, 4, 5, 6 });
        bootExtra.setDisp_cash_result("Y");
        return bootExtra;
    }

    private void toRxObservable() {
        addDisposable(RxBus.toObservable().subscribe(o -> {
            if (o instanceof RxEventShippingSelected) {
                ShippingDataModel shippingData = ((RxEventShippingSelected) o).getShipping();
                setupShippingData(shippingData);
            } else if (o instanceof RxEventCouponSelected) {
                CouponDataModel couponData = ((RxEventCouponSelected) o).getCoupon();
                if (couponData.getNo() != 0) {
                    selectedCoupon = couponData;
                } else {
                    selectedCoupon = null;
                }
                handleCouponBox();
                initPoint();
                setupTotalPrice();
            } else if (o instanceof RxEventMemoSelected) {
                int position = ((RxEventMemoSelected) o).getPosition();
                String memo = ((RxEventMemoSelected) o).getMemo();
                if (position == 0) {
                    selectedMemo = "";
                    view.showDirectMemoEditText();
                } else {
                    selectedMemo = memo;
                    view.hideDirectMemoEditText();
                }
                handleMemoBox();
            } else if (o instanceof ShippingPresenterImpl.RxEventShippingUpdated) {
                reqOrderSheet();
            } else if (o instanceof ShippingPresenterImpl.RxEventShippingDeleteClick) {
                ShippingDataModel shippingData = ((ShippingPresenterImpl.RxEventShippingDeleteClick) o).getData();
                if (shippingData.getAddressNo() > 0
                    && shippingData.getAddressNo() == selectedShippingData.getAddressNo()) {
                    isSelectedShipping = false;
                    selectedShippingData = null;
                    setupShippingData(null);
                }
            }
        }, Timber::e));
    }

    @AllArgsConstructor @Getter public final static class RxEventShippingSelected {
        private ShippingDataModel shipping;
    }

    @AllArgsConstructor @Getter public final static class RxEventCouponSelected {
        private CouponDataModel coupon;
    }

    @AllArgsConstructor @Getter public final static class RxEventMemoSelected {
        private int position;
        private String memo;
    }
}