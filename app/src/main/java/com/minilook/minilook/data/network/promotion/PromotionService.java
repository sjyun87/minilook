package com.minilook.minilook.data.network.promotion;

import com.minilook.minilook.data.model.market.MarketPromotionDataModel;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PromotionService {

    @GET("minilookAction.do") Single<MarketPromotionDataModel> getPromotion(
        @Query("process") String process,
        @Query("prm_id") int prm_id
    );
}
