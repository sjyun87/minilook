package com.minilook.minilook.data.network.common;

import com.minilook.minilook.data.model.base.BaseDataModel;
import io.reactivex.rxjava3.core.Single;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface CommonService {

    @GET("/api/commons/orderbys") Single<BaseDataModel> getSortCode();

    @POST("/api/commons/appversions") Single<BaseDataModel> checkVersion(
        @Body RequestBody body
    );
}
