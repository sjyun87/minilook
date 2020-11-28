package com.minilook.minilook.data.network.scrap;

import com.minilook.minilook.data.model.base.BaseDataModel;
import io.reactivex.rxjava3.core.Single;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ScrapService {

    @POST("/api/members/{memberNo}/scrabs/products") Single<BaseDataModel> getScrapProducts(
        @Path("memberNo") int memberNo,
        @Body RequestBody body
    );

    @POST("/api/members/{memberNo}/scrabs/brands") Single<BaseDataModel> getScrapBrands(
        @Path("memberNo") int memberNo,
        @Body RequestBody body
    );

    @POST("/api/members/scrabs/products") Single<BaseDataModel> productScrapOn(
        @Body RequestBody body
    );

    @HTTP(method = "DELETE", path = "/api/members/scrabs/products", hasBody = true)
    Single<BaseDataModel> productScrapOff(
        @Body RequestBody body
    );

    @POST("/api/members/scrabs/brands") Single<BaseDataModel> brandScrapOn(
        @Body RequestBody body
    );

    @HTTP(method = "DELETE", path = "/api/members/scrabs/brands", hasBody = true)
    Single<BaseDataModel> brandScrapOff(
        @Body RequestBody body
    );
}
