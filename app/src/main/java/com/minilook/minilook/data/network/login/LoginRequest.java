package com.minilook.minilook.data.network.login;

import com.minilook.minilook.App;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.user.UserDataModel;
import com.minilook.minilook.data.network.base.BaseRequest;
import io.reactivex.rxjava3.core.Single;
import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends BaseRequest<LoginService> {

    @Override protected Class<LoginService> getService() {
        return LoginService.class;
    }

    public Single<BaseDataModel> login(UserDataModel model) {
        return getApi().login(createRequestBody(parseToLoginJson(model)));
    }

    private Map<String, Object> parseToLoginJson(UserDataModel model) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("snsAccount", model.getSns_id());
        jsonMap.put("snsTypeCode", model.getType());
        return jsonMap;
    }

    public Single<BaseDataModel> join(UserDataModel model) {
        return getApi().join(createRequestBody(parseToJoinJson(model)));
    }

    private Map<String, Object> parseToJoinJson(UserDataModel model) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("snsAccount", model.getSns_id());
        jsonMap.put("snsTypeCode", model.getType());
        jsonMap.put("email", model.getEmail());
        jsonMap.put("name", model.getName());
        jsonMap.put("birthDate", model.getBirth());
        jsonMap.put("phoneNumber", model.getPhone());
        jsonMap.put("ci", model.getCi());
        jsonMap.put("pushToken", App.getInstance().getPushToken());
        jsonMap.put("isAgreeCommercial", model.isCommercialInfo());
        return jsonMap;
    }

    public Single<BaseDataModel> leave() {
        int user_id = App.getInstance().getUserId();
        return getApi().leave(user_id, createRequestBody(parseToLeaveJson()));
    }

    private Map<String, Object> parseToLeaveJson() {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("snsAccount", App.getInstance().getSnsId());
        jsonMap.put("snsTypeCode", App.getInstance().getSnsType());
        return jsonMap;
    }
}
