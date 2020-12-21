package com.minilook.minilook.data.network.order;

import android.text.TextUtils;
import com.google.gson.JsonObject;
import com.minilook.minilook.App;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.order.OrderCancelDataModel;
import com.minilook.minilook.data.model.order.OrderCompleteDataModel;
import com.minilook.minilook.data.model.shopping.ShoppingBrandDataModel;
import com.minilook.minilook.data.model.shopping.ShoppingOptionDataModel;
import com.minilook.minilook.data.model.shopping.ShoppingProductDataModel;
import com.minilook.minilook.data.network.base.BaseRequest;
import io.reactivex.rxjava3.core.Single;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import okhttp3.RequestBody;
import timber.log.Timber;

public class OrderRequest extends BaseRequest<OrderService> {

    @Override protected Class<OrderService> getService() {
        return OrderService.class;
    }

    public Single<BaseDataModel> getShoppingBag() {
        int memberNo = App.getInstance().getMemberNo();
        return getApi().getShoppingBag(memberNo);
    }

    public Single<BaseDataModel> addShoppingBag(List<ShoppingOptionDataModel> goodsData) {
        int memberNo = App.getInstance().getMemberNo();
        return getApi().addShoppingBag(memberNo, createAddShoppingBagData(goodsData));
    }

    private RequestBody createAddShoppingBagData(List<ShoppingOptionDataModel> goodsData) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("options", getOptionsJson(goodsData));
        return createRequestBody(jsonMap);
    }

    private List<JsonObject> getOptionsJson(List<ShoppingOptionDataModel> goodsData) {
        List<JsonObject> options = new ArrayList<>();
        for (ShoppingOptionDataModel model : goodsData) {
            JsonObject json = new JsonObject();
            json.addProperty("optionNo", model.getOptionNo());
            json.addProperty("quantity", model.getQuantity());
            options.add(json);
        }
        return options;
    }

    public Single<BaseDataModel> updateGoodsQuantity(int shoppingbagNo, int quantity) {
        int memberNo = App.getInstance().getMemberNo();
        return getApi().updateGoodsQuantity(memberNo, shoppingbagNo, createUpdateGoodQuantityData(quantity));
    }

    private RequestBody createUpdateGoodQuantityData(int quantity) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("quantity", quantity);
        return createRequestBody(jsonMap);
    }

    public Single<BaseDataModel> deleteShoppingBag(List<Integer> deleteItem) {
        int memberNo = App.getInstance().getMemberNo();
        return getApi().deleteShoppingBag(memberNo, createDeleteShoppingBagData(deleteItem));
    }

    private RequestBody createDeleteShoppingBagData(List<Integer> deleteItem) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("carts", deleteItem);
        return createRequestBody(jsonMap);
    }

    public Single<BaseDataModel> getOrderSheet() {
        int memberNo = App.getInstance().getMemberNo();
        return getApi().getOrderSheet(memberNo);
    }

    public Single<BaseDataModel> setSafetyStock(String orderId, List<ShoppingBrandDataModel> items) {
        return getApi().setSafetyStock(createSafetyStockData(orderId, items));
    }

    private RequestBody createSafetyStockData(String orderId, List<ShoppingBrandDataModel> items) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("memberNo", App.getInstance().getMemberNo());
        jsonMap.put("mid", orderId);
        List<JsonObject> options = new ArrayList<>();
        for (ShoppingBrandDataModel brandData : items) {
            for (ShoppingProductDataModel productData : brandData.getProducts()) {
                for (ShoppingOptionDataModel optionData : productData.getOptions()) {
                    JsonObject json = new JsonObject();
                    json.addProperty("optionNo", optionData.getOptionNo());
                    json.addProperty("quantity", optionData.getQuantity());
                    options.add(json);
                }
            }
        }
        jsonMap.put("options", options);
        return createRequestBody(jsonMap);
    }

    public Single<BaseDataModel> orderComplete(OrderCompleteDataModel orderCompleteDataModel) {
        return getApi().orderComplete(createOrderCompleteData(orderCompleteDataModel));
    }

    private RequestBody createOrderCompleteData(OrderCompleteDataModel data) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("address1", data.getAddress());
        jsonMap.put("address2", data.getAddress_detail());
        jsonMap.put("addressNo", data.getAddress_id());
        jsonMap.put("brandShippings", data.getBrand_shipping());
        jsonMap.put("completedOrderOptions", data.getComplete_option());
        if (data.getUse_coupon_value() != 0) {
            jsonMap.put("couponNo", data.getCoupon_id());
            jsonMap.put("couponValue", data.getUse_coupon_value());
        }
        jsonMap.put("isDirectOrder", data.isDirectOrder());
        jsonMap.put("memberNo", data.getUser_id());
        jsonMap.put("mid", data.getOrder_id());
        jsonMap.put("paymentPrice", data.getPayment_price());
        if (data.getUse_point_value() != 0) jsonMap.put("point", data.getUse_point_value());
        jsonMap.put("productDiscountPrice", data.getTotal_discount_price());
        jsonMap.put("productTotalPrice", data.getTotal_product_price());
        if (!TextUtils.isEmpty(data.getReceipt_id())) jsonMap.put("receiptId", data.getReceipt_id());
        jsonMap.put("recipientName", data.getReceipt_name());
        jsonMap.put("recipientPhone", data.getReceipt_phone());
        jsonMap.put("shippingMemo", data.getShipping_memo());
        jsonMap.put("shippingTotalFee", data.getTotal_shipping_price());
        jsonMap.put("zipcode", data.getZip());
        Timber.e(jsonMap.toString());
        return createRequestBody(jsonMap);
    }

    public Single<BaseDataModel> getOrderHistory(long lastOrderTime, int ROWS) {
        return getApi().getOrderHistory(createOrderHistoryData(lastOrderTime, ROWS));
    }

    private RequestBody createOrderHistoryData(long lastOrderTime, int rows) {
        Map<String, Object> jsonMap = new HashMap<>();
        if (lastOrderTime != 0) jsonMap.put("lastItemOrderTime", lastOrderTime);
        jsonMap.put("memberNo", App.getInstance().getMemberNo());
        jsonMap.put("pageSize", rows);
        return createRequestBody(jsonMap);
    }

    public Single<BaseDataModel> getOrderDetail(String orderNo, String receiptId) {
        return getApi().getOrderDetail(orderNo, createOrderDetailData(orderNo, receiptId));
    }

    private RequestBody createOrderDetailData(String orderNo, String receiptId) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("memberNo", App.getInstance().getMemberNo());
        jsonMap.put("mid", orderNo);
        jsonMap.put("receiptId", receiptId);
        return createRequestBody(jsonMap);
    }

    public Single<BaseDataModel> setPurchaseConfirm(int orderOptionNo) {
        return getApi().setPurchaseConfirm(orderOptionNo);
    }

    public Single<BaseDataModel> orderCancel(OrderCancelDataModel orderData) {
        if (orderData.isAllCancel()) {
            return getApi().orderAllCancel(orderData.getOrderNo(), createAllCancelData(orderData));
        } else {
            int orderOptionNo = orderData.getGoods().get(0).getOrderOptionNo();
            return getApi().orderCancel(orderData.getOrderNo(), orderOptionNo, createCancelData(orderData));
        }
    }

    private RequestBody createAllCancelData(OrderCancelDataModel orderData) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("memberNo", App.getInstance().getMemberNo());
        jsonMap.put("mid", orderData.getOrderNo());
        if (!TextUtils.isEmpty(orderData.getReceiptId())) jsonMap.put("receiptId", orderData.getReceiptId());
        return createRequestBody(jsonMap);
    }

    private RequestBody createCancelData(OrderCancelDataModel orderData) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("memberNo", App.getInstance().getMemberNo());
        jsonMap.put("mid", orderData.getOrderNo());
        jsonMap.put("orderNo", orderData.getGoods().get(0).getOrderOptionNo());
        if (!TextUtils.isEmpty(orderData.getReceiptId())) jsonMap.put("receiptId", orderData.getReceiptId());
        return createRequestBody(jsonMap);
    }

    public Single<BaseDataModel> getExchangeNReturnCode() {
        return getApi().getExchangeNReturnCode();
    }

    public Single<BaseDataModel> exchangeNReturn(int orderOptionNo, String typeCode, String reasonCode,
        String reasonDetail) {
        return getApi().exchangeNReturn(createExchangeNReturnData(orderOptionNo, typeCode, reasonCode, reasonDetail));
    }

    private RequestBody createExchangeNReturnData(int orderOptionNo, String typeCode, String reasonCode,
        String reasonDetail) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("orderNo", orderOptionNo);
        jsonMap.put("refundTypeCode", typeCode);
        jsonMap.put("refundResonCode", reasonCode);
        jsonMap.put("memo", reasonDetail);
        return createRequestBody(jsonMap);
    }
}
