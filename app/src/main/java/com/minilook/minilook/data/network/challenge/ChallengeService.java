package com.minilook.minilook.data.network.challenge;

import com.minilook.minilook.data.model.base.BaseDataModel;
import io.reactivex.rxjava3.core.Single;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ChallengeService {

    @POST("/api/challenges/processing") Single<BaseDataModel> getOpenChallenge(
        @Body RequestBody body
    );

    @POST("/api/challenges/predestination") Single<BaseDataModel> getComingChallenge(
        @Body RequestBody body
    );

    @POST("/api/challenges/ends") Single<BaseDataModel> getCloseChallenge(
        @Body RequestBody body
    );

    @POST("/api/challenges/{challengeNo}") Single<BaseDataModel> getChallengeDetail(
        @Path("challengeNo") int challengeNo,
        @Body RequestBody body
    );

    @GET("/api/challenges/members/{memberNo}") Single<BaseDataModel> getMemberData(
        @Path("memberNo") int memberNo
    );

    @POST("/api/challenges/{challengeNo}/members/{memberNo}") Single<BaseDataModel> enterChallenge(
        @Path("challengeNo") int challengeNo,
        @Path("memberNo") int memberNo,
        @Body RequestBody body
    );
}
