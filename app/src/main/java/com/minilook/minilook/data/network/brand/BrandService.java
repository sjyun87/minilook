package com.minilook.minilook.data.network.brand;

import com.minilook.minilook.data.model.brand.BrandDataModel;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BrandService {

    @GET("minilookAction.do") Single<BrandDataModel> getBrand(
        @Query("process") String process,
        @Query("brd_id") int brd_id
    );
}
