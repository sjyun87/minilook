package com.minilook.minilook.data.network.challenge;

import com.minilook.minilook.data.model.base.BaseDataModel;
import io.reactivex.rxjava3.core.Single;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

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

    @POST("/api/commons/appversions") Single<BaseDataModel> getChallengeDetail(
        @Body RequestBody body
    );
}
