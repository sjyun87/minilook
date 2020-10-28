package com.minilook.minilook.data.network.login;

import com.minilook.minilook.App;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.member.MemberDataModel;
import com.minilook.minilook.data.network.base.BaseRequest;
import io.reactivex.rxjava3.core.Single;
import java.util.HashMap;
import java.util.Map;
import timber.log.Timber;

public class LoginRequest extends BaseRequest<LoginService> {

    @Override protected Class<LoginService> getService() {
        return LoginService.class;
    }

    public Single<BaseDataModel> login(MemberDataModel model) {
        return getApi().login(createRequestBody(parseToLoginJson(model)));
    }

    private Map<String, Object> parseToLoginJson(MemberDataModel model) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("snsAccount", model.getSnsId());
        jsonMap.put("snsTypeCode", model.getType());
        jsonMap.put("pushToken", App.getInstance().getPushToken());
        return jsonMap;
    }

    public Single<BaseDataModel> logout() {
        int user_id = App.getInstance().getMemberNo();
        return getApi().logout(user_id, createRequestBody(parseToLogoutJson()));
    }

    private Map<String, Object> parseToLogoutJson() {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("pushToken", App.getInstance().getPushToken());
        return jsonMap;
    }

    public Single<BaseDataModel> join(MemberDataModel model) {
        return getApi().join(createRequestBody(parseToJoinJson(model)));
    }

    private Map<String, Object> parseToJoinJson(MemberDataModel model) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("snsAccount", model.getSnsId());
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
        int user_id = App.getInstance().getMemberNo();
        return getApi().leave(user_id);
    }

    public Single<BaseDataModel> getPointNCoupon() {
        int user_id = App.getInstance().getMemberNo();
        return getApi().getPointNCoupon(user_id);
    }
}
