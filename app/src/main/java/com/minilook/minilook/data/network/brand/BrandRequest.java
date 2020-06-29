package com.minilook.minilook.data.network.brand;

import com.minilook.minilook.data.model.brand.BrandDataModel;
import com.minilook.minilook.data.network.base.BaseRequest;
import io.reactivex.rxjava3.core.Single;

public class BrandRequest extends BaseRequest<BrandService> {

    @Override protected Class<BrandService> getService() {
        return BrandService.class;
    }

    public Single<BrandDataModel> getBrand(int brd_id) {
        return getApi().getBrand("Brand", brd_id);
    }
}
