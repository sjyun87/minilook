package com.minilook.minilook.data.network.product;

import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.data.network.base.BaseRequest;
import io.reactivex.rxjava3.core.Single;

public class ProductRequest extends BaseRequest<ProductService> {

    @Override protected Class<ProductService> getService() {
        return ProductService.class;
    }

    public Single<ProductDataModel> getProductDetail(int id) {
        return getApi().getProductDetail("GoodsDetail", id);
    }
}
