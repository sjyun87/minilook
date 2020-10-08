package com.minilook.minilook.data.network.question;

import com.minilook.minilook.data.model.base.BaseDataModel;
import io.reactivex.rxjava3.core.Single;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface QuestionService {

    @PUT("/api/products/{productNo}/inquiries") Single<BaseDataModel> getQuestions(
        @Path("productNo") int productNo,
        @Body RequestBody body
    );

    @POST("/api/products/{productNo}/inquiries") Single<BaseDataModel> writeQuestion(
        @Path("productNo") int productNo,
        @Body RequestBody body
    );
}
