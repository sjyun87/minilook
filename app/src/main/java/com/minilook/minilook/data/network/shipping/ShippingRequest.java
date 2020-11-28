package com.minilook.minilook.data.network.shipping;

import com.minilook.minilook.App;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.shipping.ShippingDataModel;
import com.minilook.minilook.data.network.base.BaseRequest;
import io.reactivex.rxjava3.core.Single;
import java.util.HashMap;
import java.util.Map;
import okhttp3.RequestBody;

public class ShippingRequest extends BaseRequest<ShippingService> {

    @Override protected Class<ShippingService> getService() {
        return ShippingService.class;
    }

    public Single<BaseDataModel> getShippings() {
        int memberNo = App.getInstance().getMemberNo();
        return getApi().getShippings(memberNo);
    }

    public Single<BaseDataModel> deleteShipping(int addressNo) {
        int memberNo = App.getInstance().getMemberNo();
        return getApi().deleteShipping(memberNo, addressNo);
    }

    public Single<BaseDataModel> updateShipping(ShippingDataModel model) {
        int memberNo = App.getInstance().getMemberNo();
        return getApi().updateShipping(memberNo, model.getAddressNo(), createShippingData(model));
    }

    public Single<BaseDataModel> addShipping(ShippingDataModel model) {
        int memberNo = App.getInstance().getMemberNo();
        return getApi().addShipping(memberNo, createShippingData(model));
    }

    private RequestBody createShippingData(ShippingDataModel model) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("isDefault", model.isDefault());
        jsonMap.put("name", model.getName());
        jsonMap.put("phone", model.getPhone());
        jsonMap.put("zipcode", model.getZipcode());
        jsonMap.put("address1", model.getAddress());
        jsonMap.put("address2", model.getAddressDetail());
        return createRequestBody(jsonMap);
    }

    public Single<BaseDataModel> checkIsland(int addressNo) {
        return getApi().checkIsland(addressNo);
    }
}
