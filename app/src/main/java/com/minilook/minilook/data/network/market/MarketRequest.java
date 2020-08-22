package com.minilook.minilook.data.network.market;

import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.network.base.BaseRequest;
import io.reactivex.rxjava3.core.Single;

public class MarketRequest extends BaseRequest<MarketService> {

    @Override protected Class<MarketService> getService() {
        return MarketService.class;
    }

    public Single<BaseDataModel> getMarketModules() {
        return getApi().getMarketModules();
    }
}
