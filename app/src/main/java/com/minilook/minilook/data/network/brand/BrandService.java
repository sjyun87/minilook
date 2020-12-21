package com.minilook.minilook.data.network.brand;

import com.minilook.minilook.data.model.base.BaseDataModel;
import io.reactivex.rxjava3.core.Single;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BrandService {

    @POST("/api/brands") Single<BaseDataModel> getBrands(
        @Body RequestBody body
    );

    @GET("api/brands/{brandNo}") Single<BaseDataModel> getBrandDetail(
        @Path("brandNo") int brandNo
    );

    @GET("api/brands/{brandNo}") Single<BaseDataModel> getBrandDetail(
        @Path("brandNo") int brandNo,
        @Query("memberNo") int memberNo
    );

    @GET("api/brands/{brandNo}/sellers") Single<BaseDataModel> getBrandInfo(
        @Path("brandNo") int brandNo
    );
}
