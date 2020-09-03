package com.minilook.minilook.data.network.brand;

import com.minilook.minilook.App;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.network.base.BaseRequest;
import io.reactivex.rxjava3.core.Single;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BrandRequest extends BaseRequest<BrandService> {

    @Override protected Class<BrandService> getService() {
        return BrandService.class;
    }

    public Single<BaseDataModel> getBrands(List<String> styles) {
        return getApi().getBrands(createRequestBody(parseToJson(styles)));
    }

    private Map<String, Object> parseToJson(List<String> styles) {
        Map<String, Object> jsonMap = new HashMap<>();
        if (App.getInstance().isLogin()) jsonMap.put("memberNp", App.getInstance().getUserId());
        jsonMap.put("styleCode", styles);
        return jsonMap;
    }

    public Single<BaseDataModel> getBrandDetail(int id) {
        return getApi().getBrandDetail(id);
    }

    public Single<BaseDataModel> getBrandInfo(int id) {
        return getApi().getBrandInfo(id);
    }
}
