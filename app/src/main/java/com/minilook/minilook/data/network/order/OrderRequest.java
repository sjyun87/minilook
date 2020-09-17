package com.minilook.minilook.data.network.order;

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
import timber.log.Timber;

public class OrderRequest extends BaseRequest<OrderService> {

    @Override protected Class<OrderService> getService() {
        return OrderService.class;
    }

    public Single<BaseDataModel> getShoppingBag() {
        int user_id = App.getInstance().getMemberId();
        return getApi().getShoppingBag(user_id);
    }

    public Single<BaseDataModel> addShoppingBag(List<ShoppingOptionDataModel> goodsData) {
        int user_id = App.getInstance().getMemberId();
        return getApi().addShoppingBag(user_id, createRequestBody(parseToAddJson(goodsData)));
    }

    private Map<String, Object> parseToAddJson(List<ShoppingOptionDataModel> goodsData) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("options", getOptions(goodsData));
        return jsonMap;
    }

    private Object getOptions(List<ShoppingOptionDataModel> goodsData) {
        List<JsonObject> options = new ArrayList<>();
        for (ShoppingOptionDataModel model : goodsData) {
            JsonObject json = new JsonObject();
            json.addProperty("optionNo", model.getOption_id());
            json.addProperty("quantity", model.getQuantity());
            options.add(json);
        }
        return options;
    }

    public Single<BaseDataModel> updateGoodsQuantity(int shoppingbag_id, int quantity) {
        int user_id = App.getInstance().getMemberId();
        return getApi().updateGoodsQuantity(user_id, shoppingbag_id, createRequestBody(parseToUpdateJson(quantity)));
    }

    private Map<String, Object> parseToUpdateJson(int quantity) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("quantity", quantity);
        return jsonMap;
    }

    public Single<BaseDataModel> deleteShoppingBag(List<Integer> deleteItem) {
        int user_id = App.getInstance().getMemberId();
        return getApi().deleteShoppingBag(user_id, createRequestBody(parseToDeleteJson(deleteItem)));
    }

    private Map<String, Object> parseToDeleteJson(List<Integer> deleteItem) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("carts", deleteItem);
        return jsonMap;
    }

    public Single<BaseDataModel> getOrderSheet() {
        int user_id = App.getInstance().getMemberId();
        return getApi().getOrderSheet(user_id);
    }

    public Single<BaseDataModel> setSafetyStock(String orderId, List<ShoppingBrandDataModel> items) {
        return getApi().setSafetyStock(createRequestBody(parseToSafetyStockJson(orderId, items)));
    }

    private Map<String, Object> parseToSafetyStockJson(String orderId, List<ShoppingBrandDataModel> items) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("memberNo", App.getInstance().getMemberId());
        jsonMap.put("mid", orderId);
        List<JsonObject> options = new ArrayList<>();
        for (ShoppingBrandDataModel brandData : items) {
            for (ShoppingProductDataModel productData : brandData.getProducts()) {
                for (ShoppingOptionDataModel optionData : productData.getOptions()) {
                    JsonObject json = new JsonObject();
                    json.addProperty("optionNo", optionData.getOption_id());
                    json.addProperty("quantity", optionData.getQuantity());
                    options.add(json);
                }
            }
        }
        jsonMap.put("options", options);
        return jsonMap;
    }

    public Single<BaseDataModel> orderComplete(OrderCompleteDataModel orderCompleteDataModel) {
        //parseToOrderCompleteJson(orderCompleteDataModel);
        return getApi().orderComplete(createRequestBody(parseToOrderCompleteJson(orderCompleteDataModel)));
    }

    private Map<String, Object> parseToOrderCompleteJson(OrderCompleteDataModel data) {
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
        if (data.getUse_coupon_value() != 0) jsonMap.put("point", data.getUse_point_value());
        jsonMap.put("productDiscountPrice", data.getTotal_discount_price());
        jsonMap.put("productTotalPrice", data.getTotal_product_price());
        jsonMap.put("receiptId", data.getReceipt_id());
        jsonMap.put("recipientName", data.getReceipt_name());
        jsonMap.put("recipientPhone", data.getReceipt_phone());
        jsonMap.put("shippingMemo", data.getShipping_memo());
        jsonMap.put("shippingTotalFee", data.getTotal_shipping_price());
        jsonMap.put("zipcode", data.getZip());
        Timber.e(jsonMap.toString());
        return jsonMap;
    }

    public Single<BaseDataModel> getOrderHistory(long lastOrderTime, int ROWS) {
        return getApi().getOrderHistory(createRequestBody(parseToOrderHistoryJson(lastOrderTime, ROWS)));
    }

    private Map<String, Object> parseToOrderHistoryJson(long lastOrderTime, int rows) {
        Map<String, Object> jsonMap = new HashMap<>();
        if (lastOrderTime != 0) jsonMap.put("lastItemOrderTime", lastOrderTime);
        jsonMap.put("memberNo", App.getInstance().getMemberId());
        jsonMap.put("pageSize", rows);
        return jsonMap;
    }

    public Single<BaseDataModel> getOrderDetail(String order_id, String receipt_id) {
        return getApi().getOrderDetail(order_id, createRequestBody(parseToOrderDetailJson(order_id, receipt_id)));
    }

    private Map<String, Object> parseToOrderDetailJson(String order_id, String receipt_id) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("memberNo", App.getInstance().getMemberId());
        jsonMap.put("mid", order_id);
        jsonMap.put("receiptId", receipt_id);
        return jsonMap;
    }

    public Single<BaseDataModel> setPurchaseConfirm(int orderOptionNo) {
        return getApi().setPurchaseConfirm(orderOptionNo);
    }

    public Single<BaseDataModel> orderCancel(OrderCancelDataModel orderData) {
        if (orderData.getOrderNo() != null) {
            return getApi().orderAllCancel(orderData.getOrderNo(), createRequestBody(parseToAllCancelJson(orderData)));
        } else {
            int orderOptionNo = orderData.getGoods().get(0).getOrderOptionNo();
            return getApi().orderCancel(orderData.getOrderNo(), orderOptionNo,
                createRequestBody(parseToAllCancelJson(orderData)));
        }
    }

    private Map<String, Object> parseToAllCancelJson(OrderCancelDataModel orderData) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("memberNo", App.getInstance().getMemberId());
        jsonMap.put("mid", orderData.getOrderNo());
        jsonMap.put("receiptId", orderData.getReceiptId());
        return jsonMap;
    }

    private Map<String, Object> parseToCancelJson(OrderCancelDataModel orderData) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("memberNo", App.getInstance().getMemberId());
        jsonMap.put("mid", orderData.getOrderNo());
        jsonMap.put("receiptId", orderData.getReceiptId());
        jsonMap.put("orderNo", orderData.getGoods().get(0).getOrderOptionNo());
        return jsonMap;
    }

    public Single<BaseDataModel> getExchangeNReturnCode() {
        return getApi().getExchangeNReturnCode();
    }

    public Single<BaseDataModel> exchangeNReturn(int orderOptionNo, String typeCode, String reasonCode,
        String reasonDetail) {
        return getApi().exchangeNReturn(
            createRequestBody(parseToExchangeNReturnJson(orderOptionNo, typeCode, reasonCode, reasonDetail)));
    }

    private Map<String, Object> parseToExchangeNReturnJson(int orderOptionNo, String typeCode, String reasonCode,
        String reasonDetail) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("orderNo", orderOptionNo);
        jsonMap.put("refundTypeCode", typeCode);
        jsonMap.put("refundResonCode", reasonCode);
        jsonMap.put("memo", reasonDetail);
        return jsonMap;
    }
}
