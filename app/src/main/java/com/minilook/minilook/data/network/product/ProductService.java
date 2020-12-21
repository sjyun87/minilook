package com.minilook.minilook.data.network.product;

import com.minilook.minilook.data.model.base.BaseDataModel;
import io.reactivex.rxjava3.core.Single;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ProductService {

    @POST("/api/products/{productNo}") Single<BaseDataModel> getProductDetail(
        @Path("productNo") int productNo,
        @Body RequestBody body
    );

    @GET("/api/products/{productNo}/options") Single<BaseDataModel> getProductOptions(
        @Path("productNo") int productNo
    );
}
