package com.minilook.minilook.data.network.review;

import android.text.TextUtils;
import com.minilook.minilook.App;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.review.ReviewWriteDataModel;
import com.minilook.minilook.data.network.base.BaseRequest;
import io.reactivex.rxjava3.core.Single;
import java.util.HashMap;
import java.util.Map;
import okhttp3.RequestBody;

public class ReviewRequest extends BaseRequest<ReviewService> {

    @Override protected Class<ReviewService> getService() {
        return ReviewService.class;
    }

    public Single<BaseDataModel> writeReview(ReviewWriteDataModel model) {
        return getApi().writeReview(model.getProductNo(), model.getOptionNo(), createWriteReviewData(model));
    }

    private RequestBody createWriteReviewData(ReviewWriteDataModel model) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("memberNo", App.getInstance().getMemberNo());
        jsonMap.put("mid", model.getOrderNo());
        if (model.getPhotos() != null && model.getPhotos().size() > 0) jsonMap.put("photos", model.getPhotos());
        jsonMap.put("review", model.getReview());
        Map<String, Object> detailMap = new HashMap<>();
        detailMap.put("reviewSatisfactionCode", model.getSatisfactionCode());
        detailMap.put("reviewSizeCode", model.getSizeRatingCode());
        if (!TextUtils.isEmpty(model.getGenderCode())) detailMap.put("reviewSexCode", model.getGenderCode());
        if (model.getAge() != -1) detailMap.put("age", model.getAge());
        if (model.getHeight() != -1) detailMap.put("height", model.getHeight());
        if (model.getWeight() != -1) detailMap.put("weight", model.getWeight());
        jsonMap.put("reviewDetail", detailMap);
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

    public Single<BaseDataModel> getWrittenReviews(int rows, int lastReviewNo) {
        int memberNo = App.getInstance().getMemberNo();
        return getApi().getWrittenReviews(memberNo, createGetWrittenReviewsData(rows, lastReviewNo));
    }

    private RequestBody createGetWrittenReviewsData(int rows, int lastReviewNo) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("memberNo", App.getInstance().getMemberNo());
        jsonMap.put("pageSize", rows);
        if (lastReviewNo != 0) jsonMap.put("lastReviewNo", lastReviewNo);
        return createRequestBody(jsonMap);
    }
}