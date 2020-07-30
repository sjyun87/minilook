package com.minilook.minilook.data.network.brand;

import com.minilook.minilook.data.model.brand.BrandDetailDataModel;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BrandService {

    @GET("minilookAction.do") Single<BrandDetailDataModel> getBrand(
        @Query("process") String process,
        @Query("brd_id") int id
    );
}
