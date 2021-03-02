package com.minilook.minilook.data.network.common;

import com.minilook.minilook.data.model.base.BaseDataModel;
import io.reactivex.rxjava3.core.Single;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CommonService {

    @POST("/api/commons/appversions") Single<BaseDataModel> checkVersion(
        @Body RequestBody body
    );

    @GET("/api/commons/orderbys") Single<BaseDataModel> getSortCode();

    @PUT("/api/commons/tokens") Single<BaseDataModel> updateToken(
        @Body RequestBody body
    );

    @POST("/api/commons/keys") Single<BaseDataModel> getKeys(
        @Body RequestBody body
    );

    @POST("/api/members/{memberNo}/events/coupons") Single<BaseDataModel> registCoupon(
        @Path("memberNo") int memberNo,
        @Body RequestBody body
    );
}
