package com.minilook.minilook.data.network.member;

import com.minilook.minilook.data.model.base.BaseDataModel;
import io.reactivex.rxjava3.core.Single;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface MemberService {

    @PUT("/api/members/{user_id}") Single<BaseDataModel> updateToken(
        @Path("user_id") int user_id,
        @Body RequestBody body
    );

    @POST("/api/nonmembers/pushtokens") Single<BaseDataModel> updateToken(
        @Body RequestBody body
    );

    @PUT("/api/members/{user_id}/informationpushes") Single<BaseDataModel> updateInfoNotify(
        @Path("user_id") int user_id,
        @Body RequestBody body
    );

    @PUT("/api/nonmembers/pushtokens") Single<BaseDataModel> updateMarketing(
        @Body RequestBody body
    );
}
