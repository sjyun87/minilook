package com.minilook.minilook.data.network.promotion;

import com.minilook.minilook.App;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.network.base.BaseRequest;
import io.reactivex.rxjava3.core.Single;
import java.util.HashMap;
import java.util.Map;
import okhttp3.RequestBody;

public class PromotionRequest extends BaseRequest<PromotionService> {

    @Override protected Class<PromotionService> getService() {
        return PromotionService.class;
    }

    public Single<BaseDataModel> getPromotionDetail(int promotionNo) {
        return getApi().getPromotionDetail(promotionNo, createPromotionDetailData(promotionNo));
    }

    private RequestBody createPromotionDetailData(int promotionNo) {
        Map<String, Object> jsonMap = new HashMap<>();
        if (App.getInstance().isLogin()) jsonMap.put("memberNo", App.getInstance().getMemberNo());
        jsonMap.put("pageSize", 0);
        jsonMap.put("promotionNo", promotionNo);
        return createRequestBody(jsonMap);
    }

    public Single<BaseDataModel> getPromotions(int promotionNo, int rows) {
        return getApi().getPromotions(createRequestBody(createGetPromotionsData(promotionNo, rows)));
    }

    public Single<BaseDataModel> getPromotions(int promotionNo, int rows, int lastPromotionNo) {
        return getApi().getPromotions(createRequestBody(createGetPromotionsData(promotionNo, rows, lastPromotionNo)));
    }

    private Map<String, Object> createGetPromotionsData(int promotionNo, int rows) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("promotionNo", promotionNo);
        jsonMap.put("pageSize", rows);
        return jsonMap;
    }

    private Map<String, Object> createGetPromotionsData(int promotionNo, int rows, int lastPromotionNo) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("promotionNo", promotionNo);
        jsonMap.put("pageSize", rows);
        jsonMap.put("pagePromotionNo", lastPromotionNo);
        return jsonMap;
    }
}
