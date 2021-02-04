package com.minilook.minilook.data.network.common;

import com.minilook.minilook.App;
import com.minilook.minilook.BuildConfig;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.network.base.BaseRequest;
import io.reactivex.rxjava3.core.Single;
import java.util.HashMap;
import java.util.Map;
import okhttp3.RequestBody;

public class CommonRequest extends BaseRequest<CommonService> {

    @Override protected Class<CommonService> getService() {
        return CommonService.class;
    }

    public Single<BaseDataModel> checkVersion() {
        return getApi().checkVersion(createCheckVersionData());
    }

    private RequestBody createCheckVersionData() {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("appName", "minilook-a");
        jsonMap.put("appVersion", BuildConfig.VERSION_NAME);
        return createRequestBody(jsonMap);
    }

    public Single<BaseDataModel> getSortCode() {
        return getApi().getSortCode();
    }

    public Single<BaseDataModel> updateToken(String token) {
        return getApi().updateToken(createUpdateTokenData(token));
    }

    private RequestBody createUpdateTokenData(String token) {
        Map<String, Object> jsonMap = new HashMap<>();
        if (App.getInstance().isLogin()) jsonMap.put("memberNo", App.getInstance().getMemberNo());
        jsonMap.put("pushToken", token);
        return createRequestBody(jsonMap);
    }

    public Single<BaseDataModel> getKeys() {
        return getApi().getKeys(createGetKeyData());
    }

    private RequestBody createGetKeyData() {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("accessToken", "dkdlrkxmrqufgowlsmswnans");
        return createRequestBody(jsonMap);
    }
}
