package com.minilook.minilook.data.network.login;

import com.minilook.minilook.data.model.base.BaseDataModel;
import io.reactivex.rxjava3.core.Single;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginService {

    @POST("/api/members") Single<BaseDataModel> join(
        @Body RequestBody body
    );

    @POST("/api/members/existing") Single<BaseDataModel> login(
        @Body RequestBody body
    );
}
