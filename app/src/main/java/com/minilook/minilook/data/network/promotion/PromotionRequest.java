package com.minilook.minilook.data.network.promotion;

import com.minilook.minilook.data.model.market.MarketPromotionDataModel;
import com.minilook.minilook.data.network.base.BaseRequest;

import io.reactivex.rxjava3.core.Single;

public class PromotionRequest extends BaseRequest<PromotionService> {

    @Override protected Class<PromotionService> getService() {
        return PromotionService.class;
    }

    public Single<MarketPromotionDataModel> getPromotion(int prm_id) {
        return getApi().getPromotion("Promotion", prm_id);
    }
}
