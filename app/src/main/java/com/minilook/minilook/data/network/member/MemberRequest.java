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
        int user_id = App.getInstance().getUserId();
        return getApi().getProfile(user_id);
    }

    public Single<BaseDataModel> updateNick(String nick) {
        int user_id = App.getInstance().getUserId();
        return getApi().updateNick(user_id, createRequestBody(parseToUpdateNick(nick)));
    }

    private Map<String, Object> parseToUpdateNick(String nick) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("nickname", nick);
        return jsonMap;
    }

    public Single<BaseDataModel> updatePhone(String phone, String ci) {
        int user_id = App.getInstance().getUserId();
        return getApi().updateNick(user_id, createRequestBody(parseToUpdateNick(phone, ci)));
    }

    private Map<String, Object> parseToUpdateNick(String phone, String ci) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("ci", ci);
        jsonMap.put("phone", phone);
        return jsonMap;
    }








    public Single<BaseDataModel> updateToken() {
        if (App.getInstance().isLogin()) {
            return getApi().updateToken(App.getInstance().getUserId(), createRequestBody(parseUpdateTokenJson(true)));
        } else {
            return getApi().updateToken(createRequestBody(parseUpdateTokenJson(false)));
        }
    }

    public Single<BaseDataModel> updateInfoNotify(boolean enable) {
        //return getApi().getScrapbookProduct(App.getInstance().getUserId());
        return getApi().updateInfoNotify(85, createRequestBody(parseInfoNotifyJson(enable)));
    }

    public Single<BaseDataModel> updateMarketing(boolean enable) {
        return getApi().updateMarketing(createRequestBody(parseMarketingNotifyJson(enable)));
    }

    private Map<String, Object> parseUpdateTokenJson(boolean isLogin) {
        Map<String, Object> jsonMap = new HashMap<>();
        if (isLogin) {
            jsonMap.put("snsAccount", App.getInstance().getSnsId());
            jsonMap.put("snsTypeCode", App.getInstance().getSnsType());
        }
        jsonMap.put("token", App.getInstance().getPushToken());
        return jsonMap;
    }

    private Map<String, Object> parseInfoNotifyJson(boolean enable) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("isInformationAgree", enable);
        return jsonMap;
    }

    private Map<String, Object> parseMarketingNotifyJson(boolean enable) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("token", App.getInstance().getPushToken());
        jsonMap.put("isMarketingAgree", enable);
        return jsonMap;
    }
}
