package com.minilook.minilook.data.network.scrap;

import com.minilook.minilook.data.model.base.BaseDataModel;
import io.reactivex.rxjava3.core.Single;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ScrapService {

    @POST("/api/members/{user_id}/scrabs/products") Single<BaseDataModel> getScrapProducts(
        @Path("user_id") int user_id,
        @Body RequestBody body
    );

    @POST("/api/members/{user_id}/scrabs/brands") Single<BaseDataModel> getScrapBrands(
        @Path("user_id") int user_id,
        @Body RequestBody body
    );
}