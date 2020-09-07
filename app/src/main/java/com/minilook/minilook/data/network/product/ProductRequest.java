package com.minilook.minilook.data.network.product;

import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.network.base.BaseRequest;
import io.reactivex.rxjava3.core.Single;

public class ProductRequest extends BaseRequest<ProductService> {

    @Override protected Class<ProductService> getService() {
        return ProductService.class;
    }

    public Single<BaseDataModel> getProductDetail(int product_id) {
        return getApi().getProductDetail(product_id);
    }

    public Single<BaseDataModel> getProductOptions(int id) {
        return getApi().getProductOptions(id);
    }
}
