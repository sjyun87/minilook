package com.minilook.minilook.data.network.shipping;

import com.minilook.minilook.data.model.base.BaseDataModel;
import io.reactivex.rxjava3.core.Single;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ShippingService {

    @GET("/api/members/{user_id}/addresses") Single<BaseDataModel> getShippings(
        @Path("user_id") int user_id
    );

    @DELETE("/api/members/{user_id}/addresses/{address_id}") Single<BaseDataModel> deleteShipping(
        @Path("user_id") int user_id,
        @Path("address_id") int address_id
    );

    @PUT("/api/members/{user_id}/addresses/{address_id}") Single<BaseDataModel> updateShipping(
        @Path("user_id") int user_id,
        @Path("address_id") int address_id,
        @Body RequestBody body
    );

    @POST("/api/members/{user_id}/addresses") Single<BaseDataModel> addShipping(
        @Path("user_id") int user_id,
        @Body RequestBody body
    );

    @GET("/api/shipping/addresses/{address_id}/islands") Single<BaseDataModel> checkIsland(
        @Path("address_id") int address_id
    );
}
