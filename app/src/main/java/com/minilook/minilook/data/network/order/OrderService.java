package com.minilook.minilook.data.network.order;

import com.minilook.minilook.data.model.base.BaseDataModel;
import io.reactivex.rxjava3.core.Single;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface OrderService {

    @GET("/api/members/{user_id}/carts") Single<BaseDataModel> getShoppingBag(
        @Path("user_id") int user_id
    );

    @POST("/api/members/{user_id}/carts")Single<BaseDataModel> addShoppingBag(
        @Path("user_id") int user_id,
        @Body RequestBody requestBody
    );
}
