package com.minilook.minilook.data.network.member;

import com.minilook.minilook.App;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.user.UserDataModel;
import com.minilook.minilook.data.network.base.BaseRequest;
import io.reactivex.rxjava3.core.Single;
import java.util.HashMap;
import java.util.Map;

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
        if (App.getInstance().isLogin()) {
            return getApi().updateToken(App.getInstance().getUserId(), createRequestBody(parseUpdateTokenJson(true)));
        } else {
            return getApi().updateToken(createRequestBody(parseUpdateTokenJson(false)));
        }
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

    public Single<BaseDataModel> updateNonUserMarketing(boolean enable) {
        return getApi().updateToken(createRequestBody(parseNonUserMarketingJson(enable)));
    }

    private Map<String, Object> parseNonUserMarketingJson(boolean enable) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("token", App.getInstance().getPushToken());
        jsonMap.put("isMarketingAgree", enable);
        return jsonMap;
    }

    public Single<BaseDataModel> getIpage() {
        //return getApi().getIpage(App.getInstance().getUserId());
        return getApi().getIpage(87);
    }

    public Single<BaseDataModel> getScrapProducts(int page, int rows) {
        //return getApi().getScrapbookProduct(App.getInstance().getUserId());
        return getApi().getScrapProducts(85, createRequestBody(parseToScrapJson(page, rows)));
    }

    public Single<BaseDataModel> getScrapBrands(int page, int rows) {
        //return getApi().getScrapbookProduct(App.getInstance().getUserId());
        return getApi().getScrapBrands(85, createRequestBody(parseToScrapJson(page, rows)));
    }

    private Map<String, Object> parseToScrapJson(int page, int rows) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("current", page);
        jsonMap.put("pageSize", rows);
        return jsonMap;
    }

    public Single<BaseDataModel> getRecentProducts(int id, int rows) {
        //return getApi().getScrapbookProduct(App.getInstance().getUserId());
        return getApi().getRecentProducts(85, createRequestBody(parseToRecentJson(id, rows)));
    }

    private Map<String, Object> parseToRecentJson(int id, int rows) {
        Map<String, Object> jsonMap = new HashMap<>();
        if (id != -1) jsonMap.put("lastRecentNo", id);
        jsonMap.put("pageSize", rows);
        return jsonMap;
    }
}
