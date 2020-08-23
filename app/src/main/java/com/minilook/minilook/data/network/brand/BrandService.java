package com.minilook.minilook.data.network.brand;

import com.minilook.minilook.data.model.base.BaseDataModel;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BrandService {

    @GET("api/brands/{brand_id}") Single<BaseDataModel> getBrand(
        @Path("brand_id") int id
    );
}
