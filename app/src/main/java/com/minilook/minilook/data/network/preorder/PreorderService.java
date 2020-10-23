package com.minilook.minilook.data.network.preorder;

import com.minilook.minilook.data.model.base.BaseDataModel;
import io.reactivex.rxjava3.core.Single;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PreorderService {

    @GET("/api/preorders/processing") Single<BaseDataModel> getOpenPreorders();

    @GET("/api/preorders/predestination") Single<BaseDataModel> getComingPreorders();

    @POST("/api/preorders/ends") Single<BaseDataModel> getClosePreorders(
        @Body RequestBody body
    );

    @GET("/api/preorders/{preorderNo}") Single<BaseDataModel> getPreorder(
        @Path("preorderNo") int preorderNo
    );

    @GET("/api/preorders/{preorderNo}/options") Single<BaseDataModel> getPreorderOptions(
        @Path("preorderNo") int preorderNo
    );
}
