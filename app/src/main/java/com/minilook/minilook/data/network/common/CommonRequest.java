package com.minilook.minilook.data.network.common;

import com.minilook.minilook.App;
import com.minilook.minilook.BuildConfig;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.network.base.BaseRequest;
import io.reactivex.rxjava3.core.Single;
import java.util.HashMap;
import java.util.Map;

public class CommonRequest extends BaseRequest<CommonService> {

    @Override protected Class<CommonService> getService() {
        return CommonService.class;
    }

    public Single<BaseDataModel> checkVersion() {
        return getApi().checkVersion(createRequestBody(getVersionData()));
    }

    private Map<String, Object> getVersionData() {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("appName", "minilook-a");
        jsonMap.put("appVersion", BuildConfig.VERSION_NAME);
        return jsonMap;
    }

    public Single<BaseDataModel> getSortCode() {
        return getApi().getSortCode();
    }

    public Single<BaseDataModel> updateToken(String token) {
        return getApi().updateToken(createRequestBody(getPushTokenData(token)));
    }

    private Map<String, Object> getPushTokenData(String token) {
        Map<String, Object> jsonMap = new HashMap<>();
        if (App.getInstance().isLogin()) jsonMap.put("memberNo", App.getInstance().getMemberNo());
        jsonMap.put("pushToken", token);
        return jsonMap;
    }
}
