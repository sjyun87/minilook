package com.minilook.minilook.data.network.question;

import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.network.base.BaseRequest;
import io.reactivex.rxjava3.core.Single;
import java.util.HashMap;
import java.util.Map;
import timber.log.Timber;

public class QuestionRequest extends BaseRequest<QuestionService> {

    @Override protected Class<QuestionService> getService() {
        return QuestionService.class;
    }

    public Single<BaseDataModel> getQuestions(int productNo, int rows, int lastQuestionNo) {
        return getApi().getQuestions(productNo, createRequestBody(parseToHistoryJson(rows, lastQuestionNo)));
    }

    private Map<String, Object> parseToHistoryJson(int rows, int lastQuestionNo) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("pageSize", rows);
        if (lastQuestionNo != 0) jsonMap.put("lastInquiryNo", lastQuestionNo);
        Timber.e(jsonMap.toString());
        return jsonMap;
    }
}