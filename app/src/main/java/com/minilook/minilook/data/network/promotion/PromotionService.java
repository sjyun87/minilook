package com.minilook.minilook.data.network.promotion;

import com.minilook.minilook.data.model.base.BaseDataModel;
import io.reactivex.rxjava3.core.Single;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PromotionService {

    @POST("/api/markets/promotions/{promotion_id}") Single<BaseDataModel> getPromotionDetail(
        @Path("promotion_id") int promotion_id,
        @Body RequestBody body
    );

    @POST("/api/markets/withpromotions") Single<BaseDataModel> getPromotions(
        @Body RequestBody body
    );
}
