package com.minilook.minilook.data.network.preorder;

import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.network.base.BaseRequest;
import io.reactivex.rxjava3.core.Single;
import java.util.HashMap;
import java.util.Map;

public class PreorderRequest extends BaseRequest<PreorderService> {

    @Override protected Class<PreorderService> getService() {
        return PreorderService.class;
    }

    public Single<BaseDataModel> getOpenPreorders() {
        return getApi().getOpenPreorders();
    }

    public Single<BaseDataModel> getComingPreorders() {
        return getApi().getComingPreorders();
    }

    public Single<BaseDataModel> getClosePreorders(int rows, int lastPreorderNo) {
        return getApi().getClosePreorders(createRequestBody(parseToClosePreorder(rows, lastPreorderNo)));
    }

    private Map<String, Object> parseToClosePreorder(int rows, int lastPreorderNo) {
        Map<String, Object> jsonMap = new HashMap<>();
        if (lastPreorderNo > 0) jsonMap.put("lastPreorderNo", lastPreorderNo);
        jsonMap.put("pageSize", rows);
        return jsonMap;
    }
}
