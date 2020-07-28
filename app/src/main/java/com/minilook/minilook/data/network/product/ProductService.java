package com.minilook.minilook.data.network.product;

import com.minilook.minilook.data.model.product.ProductColorDataModel;
import com.minilook.minilook.data.model.product.ProductDataModel;
import io.reactivex.rxjava3.core.Single;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ProductService {

    @GET("minilookAction.do") Single<ProductDataModel> getProductDetail(
        @Query("process") String process,
        @Query("goods_id") int id
    );

    @GET("minilookAction.do") Single<List<ProductColorDataModel>> getProductOptions(
        @Query("process") String process,
        @Query("goods_id") int id
    );
}
