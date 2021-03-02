package com.minilook.minilook.data.network.question;

import com.minilook.minilook.App;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.question.QuestionDataModel;
import com.minilook.minilook.data.model.question.QuestionWriteDataModel;
import com.minilook.minilook.data.network.base.BaseRequest;
import io.reactivex.rxjava3.core.Single;
import java.util.HashMap;
import java.util.Map;
import okhttp3.RequestBody;

public class QuestionRequest extends BaseRequest<QuestionService> {

    @Override protected Class<QuestionService> getService() {
        return QuestionService.class;
    }

    public Single<BaseDataModel> getQuestions(int productNo, int rows, int lastQuestionNo) {
        return getApi().getQuestions(productNo, createGetQuestionsData(rows, lastQuestionNo));
    }

    private RequestBody createGetQuestionsData(int rows, int lastQuestionNo) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("pageSize", rows);
        if (lastQuestionNo != 0) jsonMap.put("lastInquiryNo", lastQuestionNo);
        return createRequestBody(jsonMap);
    }

    public Single<BaseDataModel> writeQuestion(QuestionWriteDataModel model) {
        return getApi().writeQuestion(model.getProductNo(), createWriteQuestionData(model));
    }

    private RequestBody createWriteQuestionData(QuestionWriteDataModel model) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("memberNo", App.getInstance().getMemberNo());
        jsonMap.put("type", model.getType());
        jsonMap.put("content", model.getQuestion());
        if (model.getPhotos() != null && model.getPhotos().size() > 0) jsonMap.put("photos", model.getPhotos());
        jsonMap.put("secret", model.isSecret());
        return createRequestBody(jsonMap);
    }

    public Single<BaseDataModel> getQuestionHistory(int row, int lastQuestionNo) {
        int memberNo = App.getInstance().getMemberNo();
        return getApi().getQuestionHistory(memberNo, createQuestionHistoryData(row, lastQuestionNo));
    }

    private RequestBody createQuestionHistoryData(int rows, int lastQuestionNo) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("pageSize", rows);
        if (lastQuestionNo != 0) jsonMap.put("lastInquiryNo", lastQuestionNo);
        return createRequestBody(jsonMap);
    }

    public Single<BaseDataModel> editQuestion(QuestionWriteDataModel model) {
        return getApi().editQuestion(model.getQuestionNo(), createEditQuestionData(model));
    }

    private RequestBody createEditQuestionData(QuestionWriteDataModel model) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("memberNo", App.getInstance().getMemberNo());
        jsonMap.put("type", model.getType());
        jsonMap.put("content", model.getQuestion());
        jsonMap.put("isEditedPhotos", model.isPhotoEdit());
        if (model.getPhotos() != null && model.getPhotos().size() > 0) jsonMap.put("photos", model.getPhotos());
        jsonMap.put("secret", model.isSecret());
        return createRequestBody(jsonMap);
    }

    public Single<BaseDataModel> deleteQuestion(int productNo, int questionNo) {
        return getApi().deleteQuestion(productNo, questionNo);
    }

    public Single<BaseDataModel> switchQuestionType(QuestionDataModel model) {
        return getApi().switchType(model.getProductNo(), model.getQuestionNo(), createSwitchTypeData(model));
    }

    private RequestBody createSwitchTypeData(QuestionDataModel model) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("productNo", model.getProductNo());
        jsonMap.put("inquiryNo", model.getQuestionNo());
        jsonMap.put("isSecret", model.isSecret());
        return createRequestBody(jsonMap);
    }
}