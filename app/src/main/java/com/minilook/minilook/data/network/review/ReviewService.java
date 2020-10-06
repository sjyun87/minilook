package com.minilook.minilook.data.network.review;

import com.minilook.minilook.data.model.base.BaseDataModel;
import io.reactivex.rxjava3.core.Single;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ReviewService {

    @POST("/api/products/{productNo}/options/{optionNo}/reviews") Single<BaseDataModel> writeReview(
        @Path("productNo") int productNo,
        @Path("optionNo") int optionNo,
        @Body RequestBody body
    );

    @POST("/api/products/{productNo}/members/{memberNo}/reviews/{reviewNo}/helps") Single<BaseDataModel> registHelp(
        @Path("productNo") int productNo,
        @Path("memberNo") int memberNo,
        @Path("reviewNo") int reviewNo
    );

    @DELETE("/api/products/{productNo}/members/{memberNo}/reviews/{reviewNo}/helps") Single<BaseDataModel> cancelHelp(
        @Path("productNo") int productNo,
        @Path("memberNo") int memberNo,
        @Path("reviewNo") int reviewNo
    );
}
