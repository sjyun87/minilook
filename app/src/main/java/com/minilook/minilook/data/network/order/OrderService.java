package com.minilook.minilook.data.network.order;

import com.minilook.minilook.data.model.base.BaseDataModel;
import io.reactivex.rxjava3.core.Single;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OrderService {

    @GET("/api/members/{user_id}/carts") Single<BaseDataModel> getShoppingBag(
        @Path("user_id") int user_id
    );

    @POST("/api/members/{user_id}/carts") Single<BaseDataModel> addShoppingBag(
        @Path("user_id") int user_id,
        @Body RequestBody requestBody
    );

    @PUT("/api/members/{user_id}/carts/{goods_id}") Single<BaseDataModel> updateGoodsQuantity(
        @Path("user_id") int user_id,
        @Path("goods_id") int goods_id,
        @Body RequestBody requestBody);

    @HTTP(method = "DELETE", path = "/api/members/{user_id}/carts", hasBody = true)
    Single<BaseDataModel> deleteShoppingBag(
        @Path("user_id") int user_id,
        @Body RequestBody requestBody);

    @GET("/api/orders/sheet") Single<BaseDataModel> getOrderSheet(
        @Query("memberNo") int user_id
    );

    @POST("/api/orders/safetystocks") Single<BaseDataModel> setSafetyStock(
        @Body RequestBody requestBody
    );

    @PUT("/api/orders/bootpays") Single<BaseDataModel> orderComplete(
        @Body RequestBody requestBody
    );

    @POST("/api/orders") Single<BaseDataModel> getOrderHistory(
        @Body RequestBody requestBody
    );

    @POST("/api/orders/{orderNo}") Single<BaseDataModel> getOrderDetail(
        @Path("orderNo") String orderNo,
        @Body RequestBody requestBody
    );

    @PUT("/api/orders/{orderOptionNo}") Single<BaseDataModel> setPurchaseConfirm(
        @Path("orderOptionNo") int orderOptionNo
    );

    @HTTP(method = "DELETE", path = "/api/orders/{orderNo}", hasBody = true)
    Single<BaseDataModel> orderAllCancel(
        @Path("orderNo") String orderNo,
        @Body RequestBody body
    );

    @GET("/api/orders/refunds") Single<BaseDataModel> getExchangeNReturnCode();

    @POST("/api/orders/refunds") Single<BaseDataModel> exchangeNReturn(
        @Body RequestBody body
    );
}
