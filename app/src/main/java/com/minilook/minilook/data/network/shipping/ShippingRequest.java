package com.minilook.minilook.data.network.shipping;

import com.minilook.minilook.App;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.shipping.ShippingDataModel;
import com.minilook.minilook.data.network.base.BaseRequest;
import io.reactivex.rxjava3.core.Single;
import java.util.HashMap;
import java.util.Map;

public class ShippingRequest extends BaseRequest<ShippingService> {

    @Override protected Class<ShippingService> getService() {
        return ShippingService.class;
    }

    public Single<BaseDataModel> getShippings() {
        int user_id = App.getInstance().getMemberNo();
        return getApi().getShippings(user_id);
    }

    public Single<BaseDataModel> deleteShipping(int address_id) {
        int user_id = App.getInstance().getMemberNo();
        return getApi().deleteShipping(user_id, address_id);
    }

    public Single<BaseDataModel> updateShipping(ShippingDataModel model) {
        int user_id = App.getInstance().getMemberNo();
        return getApi().updateShipping(user_id, model.getAddressNo(), createRequestBody(parseToJson(model)));
    }

    public Single<BaseDataModel> addShipping(ShippingDataModel model) {
        int user_id = App.getInstance().getMemberNo();
        return getApi().addShipping(user_id, createRequestBody(parseToJson(model)));
    }

    private Map<String, Object> parseToJson(ShippingDataModel model) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("isDefault", model.isDefault());
        jsonMap.put("name", model.getName());
        jsonMap.put("phone", model.getPhone());
        jsonMap.put("zipcode", model.getZipcode());
        jsonMap.put("address1", model.getAddress());
        jsonMap.put("address2", model.getAddressDetail());
        return jsonMap;
    }

    public Single<BaseDataModel> checkIsland(int address_id) {
        return getApi().checkIsland(address_id);
    }
}
