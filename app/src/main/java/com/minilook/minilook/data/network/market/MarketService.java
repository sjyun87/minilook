package com.minilook.minilook.data.network.market;

import com.minilook.minilook.data.model.base.BaseDataModel;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MarketService {

    @GET("/api/markets/new") Single<BaseDataModel> getMarketModules(
        @Query("memberNo") int memberNo
    );
}
