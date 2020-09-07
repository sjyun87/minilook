package com.minilook.minilook.data.network.product;

import com.minilook.minilook.data.model.base.BaseDataModel;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProductService {

    @GET("/api/products/{product_id}") Single<BaseDataModel> getProductDetail(
        @Path("product_id") int id
    );

    @GET("/api/products/{product_id}/options") Single<BaseDataModel> getProductOptions(
        @Path("product_id") int id
    );
}
