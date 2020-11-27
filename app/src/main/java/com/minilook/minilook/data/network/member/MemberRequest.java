package com.minilook.minilook.data.network.member;

import com.minilook.minilook.App;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.network.base.BaseRequest;
import io.reactivex.rxjava3.core.Single;
import java.util.HashMap;
import java.util.Map;

public class MemberRequest extends BaseRequest<MemberService> {

    @Override protected Class<MemberService> getService() {
        return MemberService.class;
    }

    public Single<BaseDataModel> getProfile() {
        int user_id = App.getInstance().getMemberNo();
        return getApi().getProfile(user_id);
    }

    public Single<BaseDataModel> updateNick(String nick) {
        int user_id = App.getInstance().getMemberNo();
        return getApi().updateNick(user_id, createRequestBody(parseToUpdateNick(nick)));
    }

    private Map<String, Object> parseToUpdateNick(String nick) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("nickname", nick);
        return jsonMap;
    }

    public Single<BaseDataModel> updatePhone(String phone, String name, String ci) {
        int user_id = App.getInstance().getMemberNo();
        return getApi().updatePhone(user_id, createRequestBody(parseToUpdatePhoneJson(phone, name, ci)));
    }

    private Map<String, Object> parseToUpdatePhoneJson(String phone, String name, String ci) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("ci", ci);
        jsonMap.put("phone", phone);
        jsonMap.put("name", name);
        return jsonMap;
    }

    public Single<BaseDataModel> getInfoStatus() {
        if (App.getInstance().isLogin()) {
            int user_id = App.getInstance().getMemberNo();
            return getApi().getInfoStatus(user_id, createRequestBody(parseToInfoJson()));
        } else {
            return getApi().getInfoStatus(createRequestBody(parseToInfoJson()));
        }
    }

    private Map<String, Object> parseToInfoJson() {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("pushToken", App.getInstance().getPushToken());
        return jsonMap;
    }

    public Single<BaseDataModel> updateOrderInfo(boolean enable) {
        int user_id = App.getInstance().getMemberNo();
        return getApi().updateOrderInfo(user_id, createRequestBody(parseToOrderInfoJson(enable)));
    }

    private Map<String, Object> parseToOrderInfoJson(boolean enable) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("isInformationAgree", enable);
        return jsonMap;
    }

    public Single<BaseDataModel> updateMarketingInfo(boolean enable) {
        if (App.getInstance().isLogin()) {
            int memberNo = App.getInstance().getMemberNo();
            return getApi().updateMarketingInfo(memberNo, createRequestBody(getMarketingData(enable)));
        } else {
            return getApi().updateMarketingInfo(createRequestBody(getMarketingData(enable)));
        }
    }

    private Map<String, Object> getMarketingData(boolean enable) {
        Map<String, Object> jsonMap = new HashMap<>();
        if (App.getInstance().isLogin()) jsonMap.put("memberNo", App.getInstance().getMemberNo());
        jsonMap.put("pushToken", App.getInstance().getPushToken());
        jsonMap.put("isMarketingAgree", enable);
        return jsonMap;
    }
}
