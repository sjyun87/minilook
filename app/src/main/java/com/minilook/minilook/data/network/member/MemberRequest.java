package com.minilook.minilook.data.network.member;

import com.minilook.minilook.App;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.user.UserDataModel;
import com.minilook.minilook.data.network.base.BaseRequest;
import io.reactivex.rxjava3.core.Single;
import java.util.HashMap;
import java.util.Map;
import timber.log.Timber;

public class MemberRequest extends BaseRequest<MemberService> {

    @Override protected Class<MemberService> getService() {
        return MemberService.class;
    }

    public Single<BaseDataModel> checkUser(UserDataModel model) {
        return getApi().checkUser(createRequestBody(parseToCheckUserJson(model)));
    }

    private Map<String, Object> parseToCheckUserJson(UserDataModel model) {
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

    public Single<BaseDataModel> updateUserToken() {
        return getApi().updateToken(App.getInstance().getUserId(), createRequestBody(getUserTokenJson()));
    }

    private Map<String, Object> getUserTokenJson() {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("snsAccount", App.getInstance().getSnsId());
        jsonMap.put("snsTypeCode", App.getInstance().getSnsType());
        jsonMap.put("token", App.getInstance().getPushToken());
        return jsonMap;
    }

    public Single<BaseDataModel> updateNonUserToken() {
        return getApi().updateToken(createRequestBody(getNonUserTokenJson()));
    }

    private Map<String, Object> getNonUserTokenJson() {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("token", App.getInstance().getPushToken());
        Timber.e(jsonMap.toString());
        return jsonMap;
    }
}
