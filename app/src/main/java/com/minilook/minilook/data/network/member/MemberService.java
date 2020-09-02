package com.minilook.minilook.data.network.member;

import com.minilook.minilook.data.model.base.BaseDataModel;
import io.reactivex.rxjava3.core.Single;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface MemberService {

    @POST("/api/members") Single<BaseDataModel> join(
        @Body RequestBody body
    );

    @POST("/api/members/existing") Single<BaseDataModel> checkUser(
        @Body RequestBody body
    );

    @PUT("/api/members/{user_id}") Single<BaseDataModel> updateToken(
        @Path("user_id") int user_id,
        @Body RequestBody body
    );

    @POST("/api/nonmembers/pushtokens") Single<BaseDataModel> updateToken(
        @Body RequestBody body
    );

    @GET("/api/members/{user_id}") Single<BaseDataModel> getIpage(
        @Path("user_id") int userId
    );

    @POST("/api/members/{user_id}/scrabs/products") Single<BaseDataModel> getScrapProducts(
        @Path("user_id") int user_id,
        @Body RequestBody body
    );

    @POST("/api/members/{user_id}/scrabs/brands") Single<BaseDataModel> getScrapBrands(
        @Path("user_id") int user_id,
        @Body RequestBody body
    );

    @POST("/api/members/{user_id}/latestproducts") Single<BaseDataModel> getRecentProducts(
        @Path("user_id") int user_id,
        @Body RequestBody body
    );
}
