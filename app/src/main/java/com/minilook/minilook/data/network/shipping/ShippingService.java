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

    @GET("/api/members/{memberNo}/addresses") Single<BaseDataModel> getShippings(
        @Path("memberNo") int memberNo
    );

    @DELETE("/api/members/{memberNo}/addresses/{addressNo}") Single<BaseDataModel> deleteShipping(
        @Path("memberNo") int memberNo,
        @Path("addressNo") int addressNo
    );

    @PUT("/api/members/{memberNo}/addresses/{addressNo}") Single<BaseDataModel> updateShipping(
        @Path("memberNo") int memberNo,
        @Path("addressNo") int addressNo,
        @Body RequestBody body
    );

    @POST("/api/members/{memberNo}/addresses") Single<BaseDataModel> addShipping(
        @Path("memberNo") int memberNo,
        @Body RequestBody body
    );

    @GET("/api/shipping/addresses/{addressNo}/islands") Single<BaseDataModel> checkIsland(
        @Path("addressNo") int addressNo
    );
}
