package com.minilook.minilook.data.network.question;

import com.minilook.minilook.App;
import com.minilook.minilook.data.model.base.BaseDataModel;
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

    public Single<BaseDataModel> writeQuestion(int productNo, String contents, String type, boolean isSecret) {
        return getApi().writeQuestion(productNo, createWriteQuestionData(contents, type, isSecret));
    }

    private RequestBody createWriteQuestionData(String contents, String type, boolean isSecret) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("memberNo", App.getInstance().getMemberNo());
        jsonMap.put("content", contents);
        jsonMap.put("type", type);
        jsonMap.put("secret", isSecret);
        return createRequestBody(jsonMap);
    }
}