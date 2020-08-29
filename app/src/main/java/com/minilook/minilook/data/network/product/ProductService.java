package com.minilook.minilook.data.network.product;

import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.product.ProductColorDataModel;
import com.minilook.minilook.data.model.product.ProductDataModel;
import io.reactivex.rxjava3.core.Single;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProductService {

    @GET("/api/products/{product_id}") Single<BaseDataModel> getProductDetail(
        @Path("product_id") int id
    );

    @GET("minilookAction.do") Single<List<ProductColorDataModel>> getProductOptions(
        @Query("process") String process,
        @Query("goods_id") int id
    );
}
