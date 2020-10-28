package com.minilook.minilook.data.network.login;

import com.minilook.minilook.data.model.base.BaseDataModel;
import io.reactivex.rxjava3.core.Single;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface LoginService {

    @POST("/api/members") Single<BaseDataModel> join(
        @Body RequestBody body
    );

    @HTTP(method = "DELETE", path = "/api/members/{user_id}", hasBody = true)
    Single<BaseDataModel> leave(
        @Path("user_id") int user_id
    );

    @POST("/api/members/existing") Single<BaseDataModel> login(
        @Body RequestBody body
    );

    @HTTP(method = "DELETE", path = "/api/members/{user_id}/pushtokens", hasBody = true)
    Single<BaseDataModel> logout(
        @Path("user_id") int user_id,
        @Body RequestBody body
    );

    @GET("/api/members/{user_id}/points/coupons")
    Single<BaseDataModel> getPointNCoupon(
        @Path("user_id") int user_id
    );
}
