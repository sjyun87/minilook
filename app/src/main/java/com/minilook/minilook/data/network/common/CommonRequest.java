package com.minilook.minilook.data.network.common;

import com.minilook.minilook.App;
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
        return getApi().checkVersion(createRequestBody(parseToVersionJson(version)));
    }

    private Map<String, Object> parseToVersionJson(String version) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("appName", "minilook-a");
        jsonMap.put("appVersion", version);
        return jsonMap;
    }

    public Single<BaseDataModel> updateToken(String token) {
        return getApi().updateToken(createRequestBody(parseToTokenJson(token)));
    }

    private Map<String, Object> parseToTokenJson(String token) {
        Map<String, Object> jsonMap = new HashMap<>();
        if (App.getInstance().isLogin()) jsonMap.put("memberNo", App.getInstance().getMemberNo());
        jsonMap.put("pushToken", token);
        return jsonMap;
    }
}
