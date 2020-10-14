package com.minilook.minilook.data.network.product;

import com.minilook.minilook.App;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.network.base.BaseRequest;
import io.reactivex.rxjava3.core.Single;
import java.util.HashMap;
import java.util.Map;

public class ProductRequest extends BaseRequest<ProductService> {

    @Override protected Class<ProductService> getService() {
        return ProductService.class;
    }

    public Single<BaseDataModel> getProductDetail(int productNo) {
        return getApi().getProductDetail(productNo, createRequestBody(parseToProductDetailJson()));
    }

    public Single<BaseDataModel> getProductDetail(int productNo, int preorderNo) {
        return getApi().getProductDetail(productNo, createRequestBody(parseToProductDetailJson(preorderNo)));
    }

    private Map<String, Object> parseToProductDetailJson() {
        Map<String, Object> jsonMap = new HashMap<>();
        if (App.getInstance().isLogin()) jsonMap.put("memberNo", App.getInstance().getMemberNo());
        return jsonMap;
    }

    private Map<String, Object> parseToProductDetailJson(int preorderNo) {
        Map<String, Object> jsonMap = new HashMap<>();
        if (App.getInstance().isLogin()) jsonMap.put("memberNo", App.getInstance().getMemberNo());
        jsonMap.put("preorderNo", preorderNo);
        return jsonMap;
    }

    public Single<BaseDataModel> getProductOptions(int id) {
        return getApi().getProductOptions(id);
    }
}
