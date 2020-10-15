package com.minilook.minilook.data.network.preorder;

import com.minilook.minilook.data.model.base.BaseDataModel;
import io.reactivex.rxjava3.core.Single;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface PreorderService {

    @GET("/api/preorders/processing") Single<BaseDataModel> getOpenPreorders();

    @GET("/api/preorders/predestination") Single<BaseDataModel> getComingPreorders();

    @POST("/api/preorders/ends") Single<BaseDataModel> getClosePreorders(
        @Body RequestBody body
    );
}
