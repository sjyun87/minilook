package com.minilook.minilook.data.network.common;

import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.network.base.BaseRequest;
import io.reactivex.rxjava3.core.Single;
import java.util.HashMap;
import java.util.Map;

public class CommonRequest extends BaseRequest<CommonService> {

    @Override protected Class<CommonService> getService() {
        return CommonService.class;
    }

    public Single<BaseDataModel> getSortCode() {
        return getApi().getSortCode();
    }

    public Single<BaseDataModel> checkVersion(String version) {
        return getApi().checkVersion(createRequestBody(parseToJson(version)));
    }

    private Map<String, Object> parseToJson(String version) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("appName", "minilook-a");
        jsonMap.put("appVersion", version);
        return jsonMap;
    }
}
