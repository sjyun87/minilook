package com.minilook.minilook.data.network.review;

import com.minilook.minilook.App;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.network.base.BaseRequest;
import io.reactivex.rxjava3.core.Single;
import java.util.HashMap;
import java.util.Map;
import okhttp3.RequestBody;

public class ReviewRequest extends BaseRequest<ReviewService> {

    @Override protected Class<ReviewService> getService() {
        return ReviewService.class;
    }

    public Single<BaseDataModel> writeReview(String orderNo, int productNo, int optionNo, String text) {
        return getApi().writeReview(productNo, optionNo, createWriteReviewData(orderNo, text));
    }

    private RequestBody createWriteReviewData(String orderNo, String text) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("memberNo", App.getInstance().getMemberNo());
        jsonMap.put("mid", orderNo);
        jsonMap.put("review", text);
        return createRequestBody(jsonMap);
    }

    public Single<BaseDataModel> updateHelp(boolean isHelp, int productNo, int reviewNo) {
        int memberNo = App.getInstance().getMemberNo();
        if (isHelp) {
            return getApi().registHelp(productNo, memberNo, reviewNo);
        } else {
            return getApi().cancelHelp(productNo, memberNo, reviewNo);
        }
    }

    public Single<BaseDataModel> getReviews(int productNo, int rows, int lastReviewNo) {
        return getApi().getReviews(productNo, createGetReviewsData(rows, lastReviewNo));
    }

    private RequestBody createGetReviewsData(int rows, int lastReviewNo) {
        Map<String, Object> jsonMap = new HashMap<>();
        if (App.getInstance().isLogin()) jsonMap.put("memberNo", App.getInstance().getMemberNo());
        jsonMap.put("pageSize", rows);
        if (lastReviewNo != 0) jsonMap.put("lastReviewNo", lastReviewNo);
        return createRequestBody(jsonMap);
    }
}