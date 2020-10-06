package com.minilook.minilook.data.network.review;

import com.minilook.minilook.App;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.network.base.BaseRequest;
import io.reactivex.rxjava3.core.Single;
import java.util.HashMap;
import java.util.Map;

public class ReviewRequest extends BaseRequest<ReviewService> {

    @Override protected Class<ReviewService> getService() {
        return ReviewService.class;
    }

    public Single<BaseDataModel> writeReview(String orderNo, int productNo, int optionNo, String text) {
        return getApi().writeReview(productNo, optionNo, createRequestBody(parseToWriteJson(orderNo, text)));
    }

    private Map<String, Object> parseToWriteJson(String orderNo, String text) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("memberNo", App.getInstance().getMemberNo());
        jsonMap.put("mid", orderNo);
        jsonMap.put("review", text);
        return jsonMap;
    }
}