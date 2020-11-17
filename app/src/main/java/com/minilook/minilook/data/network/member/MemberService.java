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

    @GET("/api/members/{memberNo}/details") Single<BaseDataModel> getProfile(
        @Path("memberNo") int memberNo
    );

    @PUT("/api/members/{memberNo}/nicknamnes") Single<BaseDataModel> updateNick(
        @Path("memberNo") int memberNo,
        @Body RequestBody body
    );

    @PUT("/api/members/{memberNo}/contacts") Single<BaseDataModel> updatePhone(
        @Path("memberNo") int memberNo,
        @Body RequestBody body
    );

    @POST("/api/members/{memberNo}/pushtokens/agreements") Single<BaseDataModel> getInfoStatus(
        @Path("memberNo") int memberNo,
        @Body RequestBody body
    );

    @POST("/api/nonmembers/pushtokens/agreements") Single<BaseDataModel> getInfoStatus(
        @Body RequestBody body
    );

    @PUT("/api/members/{memberNo}/informationpushes") Single<BaseDataModel> updateOrderInfo(
        @Path("memberNo") int memberNo,
        @Body RequestBody body
    );

    @PUT("/api/nonmembers/pushtokens") Single<BaseDataModel> updateMarketingInfo(
        @Body RequestBody body
    );

    @PUT("/api/members/{memberNo}/marketingpushes") Single<BaseDataModel> updateMarketingInfo(
        @Path("memberNo") int memberNo,
        @Body RequestBody body
    );
}
