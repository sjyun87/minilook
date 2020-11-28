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

    @GET("/api/members/{memberNo}/carts") Single<BaseDataModel> getShoppingBag(
        @Path("memberNo") int memberNo
    );

    @POST("/api/members/{memberNo}/carts") Single<BaseDataModel> addShoppingBag(
        @Path("memberNo") int memberNo,
        @Body RequestBody requestBody
    );

    @PUT("/api/members/{memberNo}/carts/{goodsNo}") Single<BaseDataModel> updateGoodsQuantity(
        @Path("memberNo") int memberNo,
        @Path("goodsNo") int goodsNo,
        @Body RequestBody requestBody);

    @HTTP(method = "DELETE", path = "/api/members/{memberNo}/carts", hasBody = true)
    Single<BaseDataModel> deleteShoppingBag(
        @Path("memberNo") int memberNo,
        @Body RequestBody requestBody);

    @GET("/api/orders/sheet") Single<BaseDataModel> getOrderSheet(
        @Query("memberNo") int memberNo
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

    @HTTP(method = "DELETE", path = "/api/orders/{orderNo}/orders/{orderOptionNo}", hasBody = true)
    Single<BaseDataModel> orderCancel(
        @Path("orderNo") String orderNo,
        @Path("orderOptionNo") int orderOptionNo,
        @Body RequestBody body
    );

    @GET("/api/orders/refunds") Single<BaseDataModel> getExchangeNReturnCode();

    @POST("/api/orders/refunds") Single<BaseDataModel> exchangeNReturn(
        @Body RequestBody body
    );
}
