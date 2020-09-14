package com.minilook.minilook.data.network.order;

import com.google.gson.JsonObject;
import com.minilook.minilook.App;
import com.minilook.minilook.data.model.base.BaseDataModel;
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

public class OrderRequest extends BaseRequest<OrderService> {

    @Override protected Class<OrderService> getService() {
        return OrderService.class;
    }

    public Single<BaseDataModel> getShoppingBag() {
        int user_id = App.getInstance().getUserId();
        return getApi().getShoppingBag(user_id);
    }

    public Single<BaseDataModel> addShoppingBag(List<ShoppingOptionDataModel> goodsData) {
        int user_id = App.getInstance().getUserId();
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
        int user_id = App.getInstance().getUserId();
        return getApi().updateGoodsQuantity(user_id, shoppingbag_id, createRequestBody(parseToUpdateJson(quantity)));
    }

    private Map<String, Object> parseToUpdateJson(int quantity) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("quantity", quantity);
        return jsonMap;
    }

    public Single<BaseDataModel> deleteShoppingBag(List<Integer> deleteItem) {
        int user_id = App.getInstance().getUserId();
        return getApi().deleteShoppingBag(user_id, createRequestBody(parseToDeleteJson(deleteItem)));
    }

    private Map<String, Object> parseToDeleteJson(List<Integer> deleteItem) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("carts", deleteItem);
        return jsonMap;
    }

    public Single<BaseDataModel> getOrderSheet() {
        int user_id = App.getInstance().getUserId();
        return getApi().getOrderSheet(user_id);
    }

    public Single<BaseDataModel> setSafetyStock(String orderId, List<ShoppingBrandDataModel> items) {
        return getApi().setSafetyStock(createRequestBody(parseToSafetyStockJson(orderId, items)));
    }

    private Map<String, Object> parseToSafetyStockJson(String orderId, List<ShoppingBrandDataModel> items) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("memberNo", App.getInstance().getUserId());
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
        return getApi().orderComplete(createRequestBody(parseToOrderCompleteJson(orderCompleteDataModel)));
    }

    private Map<String, Object> parseToOrderCompleteJson(OrderCompleteDataModel orderCompleteDataModel) {
        Map<String, Object> jsonMap = new HashMap<>();
        //jsonMap.put("addressNo", )

        return jsonMap;
    }
}
