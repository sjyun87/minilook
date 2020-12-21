package com.minilook.minilook.data.network.member;

import com.minilook.minilook.App;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.network.base.BaseRequest;
import io.reactivex.rxjava3.core.Single;
import java.util.HashMap;
import java.util.Map;
import okhttp3.RequestBody;

public class MemberRequest extends BaseRequest<MemberService> {

    @Override protected Class<MemberService> getService() {
        return MemberService.class;
    }

    public Single<BaseDataModel> getProfile() {
        int memberNo = App.getInstance().getMemberNo();
        return getApi().getProfile(memberNo);
    }

    public Single<BaseDataModel> updateNick(String nick) {
        int memberNo = App.getInstance().getMemberNo();
        return getApi().updateNick(memberNo, createUpdateNickData(nick));
    }

    private RequestBody createUpdateNickData(String nick) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("nickname", nick);
        return createRequestBody(jsonMap);
    }

    public Single<BaseDataModel> updatePhone(String phone, String name, String ci) {
        int memberNo = App.getInstance().getMemberNo();
        return getApi().updatePhone(memberNo, createUpdatePhoneData(phone, name, ci));
    }

    private RequestBody createUpdatePhoneData(String phone, String name, String ci) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("ci", ci);
        jsonMap.put("phone", phone);
        jsonMap.put("name", name);
        return createRequestBody(jsonMap);
    }

    public Single<BaseDataModel> getInfoStatus() {
        if (App.getInstance().isLogin()) {
            int memberNo = App.getInstance().getMemberNo();
            return getApi().getInfoStatus(memberNo, createInfoData());
        } else {
            return getApi().getInfoStatus(createInfoData());
        }
    }

    private RequestBody createInfoData() {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("pushToken", App.getInstance().getPushToken());
        return createRequestBody(jsonMap);
    }

    public Single<BaseDataModel> updateOrderInfo(boolean enable) {
        int memberNo = App.getInstance().getMemberNo();
        return getApi().updateOrderInfo(memberNo, createOrderInfoData(enable));
    }

    private RequestBody createOrderInfoData(boolean enable) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("isInformationAgree", enable);
        return createRequestBody(jsonMap);
    }

    public Single<BaseDataModel> updateMarketingInfo(boolean enable) {
        if (App.getInstance().isLogin()) {
            int memberNo = App.getInstance().getMemberNo();
            return getApi().updateMarketingInfo(memberNo, createRequestBody(createMarketingData(enable)));
        } else {
            return getApi().updateMarketingInfo(createRequestBody(createMarketingData(enable)));
        }
    }

    private Map<String, Object> createMarketingData(boolean enable) {
        Map<String, Object> jsonMap = new HashMap<>();
        if (App.getInstance().isLogin()) jsonMap.put("memberNo", App.getInstance().getMemberNo());
        jsonMap.put("pushToken", App.getInstance().getPushToken());
        jsonMap.put("isMarketingAgree", enable);
        return jsonMap;
    }
}
