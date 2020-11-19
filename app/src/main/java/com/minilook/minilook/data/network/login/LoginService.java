package com.minilook.minilook.data.network.login;

import com.minilook.minilook.data.model.base.BaseDataModel;
import io.reactivex.rxjava3.core.Single;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface LoginService {

    @POST("/api/members") Single<BaseDataModel> join(
        @Body RequestBody body
    );

    @HTTP(method = "DELETE", path = "/api/members/{user_id}", hasBody = true)
    Single<BaseDataModel> leave(
        @Path("user_id") int user_id
    );

    @PUT("/api/members") Single<BaseDataModel> login(
        @Body RequestBody body
    );

    @HTTP(method = "DELETE", path = "/api/members/{memberNo}/pushtokens", hasBody = true)
    Single<BaseDataModel> logout(
        @Path("memberNo") int memberNo,
        @Body RequestBody body
    );

    @GET("/api/members/{memberNo}/points/coupons")
    Single<BaseDataModel> getPointNCoupon(
        @Path("memberNo") int memberNo
    );
}
