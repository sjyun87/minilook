package com.minilook.minilook.data.network.brand;

import com.minilook.minilook.data.model.base.BaseDataModel;
import io.reactivex.rxjava3.core.Single;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BrandService {

    @POST("/api/brands") Single<BaseDataModel> getBrands(
        @Body RequestBody body
    );

    @GET("api/brands/{brand_id}") Single<BaseDataModel> getBrandDetail(
        @Path("brand_id") int id
    );

    @GET("api/brands/{brand_id}/sellers") Single<BaseDataModel> getBrandInfo(
        @Path("brand_id") int id
    );
}
