package com.minilook.minilook.data.network.login;

import com.minilook.minilook.App;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.member.MemberDataModel;
import com.minilook.minilook.data.network.base.BaseRequest;
import io.reactivex.rxjava3.core.Single;
import java.util.HashMap;
import java.util.Map;
import okhttp3.RequestBody;

public class LoginRequest extends BaseRequest<LoginService> {

    @Override protected Class<LoginService> getService() {
        return LoginService.class;
    }

    public Single<BaseDataModel> login(MemberDataModel model) {
        return getApi().login(createLoginData(model));
    }

    private RequestBody createLoginData(MemberDataModel model) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("snsAccount", model.getSnsId());
        jsonMap.put("snsTypeCode", model.getType());
        jsonMap.put("pushToken", App.getInstance().getPushToken());
        return createRequestBody(jsonMap);
    }

    public Single<BaseDataModel> logout() {
        int memberNo = App.getInstance().getMemberNo();
        return getApi().logout(memberNo, createLogoutData());
    }

    private RequestBody createLogoutData() {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("pushToken", App.getInstance().getPushToken());
        return createRequestBody(jsonMap);
    }

    public Single<BaseDataModel> join(MemberDataModel model) {
        return getApi().join(parseToJoinJson(model));
    }

    private RequestBody parseToJoinJson(MemberDataModel model) {
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
        return createRequestBody(jsonMap);
    }

    public Single<BaseDataModel> leave() {
        int memberNo = App.getInstance().getMemberNo();
        return getApi().leave(memberNo);
    }

    public Single<BaseDataModel> getPointNCoupon() {
        int memberNo = App.getInstance().getMemberNo();
        return getApi().getPointNCoupon(memberNo);
    }
}
