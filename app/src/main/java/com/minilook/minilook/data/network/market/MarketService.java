package com.minilook.minilook.data.network.market;

import com.minilook.minilook.data.model.market.MarketDataModel;
import io.reactivex.rxjava3.core.Single;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MarketService {

    @GET("minilookAction.do") Single<List<MarketDataModel>> getMarketModules(
        @Query("process") String process
    );
}
