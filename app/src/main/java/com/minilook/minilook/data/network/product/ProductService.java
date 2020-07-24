package com.minilook.minilook.data.network.product;

import com.minilook.minilook.data.model.product.ProductDataModel;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ProductService {

    @GET("minilookAction.do") Single<ProductDataModel> getProductDetail(
        @Query("process") String process,
        @Query("goods_id") int id
    );
}
