package com.minilook.minilook.data.network.promotion;

import com.minilook.minilook.App;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.network.base.BaseRequest;
import io.reactivex.rxjava3.core.Single;
import java.util.HashMap;
import java.util.Map;

public class PromotionRequest extends BaseRequest<PromotionService> {

    @Override protected Class<PromotionService> getService() {
        return PromotionService.class;
    }

    public Single<BaseDataModel> getPromotionDetail(int promotion_id) {
        return getApi().getPromotionDetail(promotion_id,
            createRequestBody(parseToPromotionJson(promotion_id)));
    }

    private Map<String, Object> parseToPromotionJson(int promotion_id) {
        Map<String, Object> jsonMap = new HashMap<>();
        if (App.getInstance().isLogin()) jsonMap.put("memberNo", App.getInstance().getMemberNo());
        jsonMap.put("pageSize", 0);
        jsonMap.put("promotionNo", promotion_id);
        return jsonMap;
    }

    public Single<BaseDataModel> getPromotions(int promotion_id, int rows, int latestPromotionId) {
        return getApi().getPromotions(createRequestBody(parseToTogetherJson(promotion_id, rows, latestPromotionId)));
    }

    private Map<String, Object> parseToTogetherJson(int promotion_id, int rows, int latest_id) {
        Map<String, Object> jsonMap = new HashMap<>();
        if (latest_id != -1) jsonMap.put("pagePromotionNo", latest_id);
        jsonMap.put("pageSize", rows);
        jsonMap.put("promotionNo", promotion_id);
        return jsonMap;
    }
}
