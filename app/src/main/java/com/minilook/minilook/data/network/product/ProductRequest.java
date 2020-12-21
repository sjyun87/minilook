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
        return getApi().getProductDetail(productNo, createRequestBody(createProductDetailData(productNo)));
    }

    public Single<BaseDataModel> getProductDetail(int productNo, int preorderNo) {
        return getApi().getProductDetail(productNo, createRequestBody(createProductDetailData(preorderNo, productNo)));
    }

    private Map<String, Object> createProductDetailData(int productNo) {
        Map<String, Object> jsonMap = new HashMap<>();
        if (App.getInstance().isLogin()) jsonMap.put("memberNo", App.getInstance().getMemberNo());
        jsonMap.put("productNo", productNo);
        return jsonMap;
    }

    private Map<String, Object> createProductDetailData(int preorderNo, int productNo) {
        Map<String, Object> jsonMap = new HashMap<>();
        if (App.getInstance().isLogin()) jsonMap.put("memberNo", App.getInstance().getMemberNo());
        jsonMap.put("preorderNo", preorderNo);
        jsonMap.put("productNo", productNo);
        return jsonMap;
    }

    public Single<BaseDataModel> getProductOptions(int id) {
        return getApi().getProductOptions(id);
    }
}
