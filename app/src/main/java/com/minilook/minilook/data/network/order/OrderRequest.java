package com.minilook.minilook.data.network.order;

import com.google.gson.JsonObject;
import com.minilook.minilook.App;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.product.GoodsDataModel;
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

    public Single<BaseDataModel> addShoppingBag(List<GoodsDataModel> goodsData) {
        int user_id = App.getInstance().getUserId();
        return getApi().addShoppingBag(user_id, createRequestBody(parseToAddJson(goodsData)));
    }

    private Map<String, Object> parseToAddJson(List<GoodsDataModel> goodsData) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("options", getOptions(goodsData));
        return jsonMap;
    }

    private Object getOptions(List<GoodsDataModel> goodsData) {
        List<JsonObject> options = new ArrayList<>();
        for (GoodsDataModel model : goodsData) {
            JsonObject json = new JsonObject();
            json.addProperty("optionNo", model.getGoods_id());
            json.addProperty("quantity", model.getSelected_quantity());
            options.add(json);
        }
        return options;
    }
}
