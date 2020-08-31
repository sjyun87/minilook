package com.minilook.minilook.data.network.member;

import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.product.ProductColorDataModel;
import com.minilook.minilook.data.model.user.UserDataModel;
import com.minilook.minilook.data.network.base.BaseRequest;
import io.reactivex.rxjava3.core.Single;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemberRequest extends BaseRequest<MemberService> {

    @Override protected Class<MemberService> getService() {
        return MemberService.class;
    }

    public Single<BaseDataModel> checkUser(UserDataModel model) {
        return getApi().checkUser(createRequestBody(parseToJson(model)));
    }

    private Map<String, Object> parseToJson(UserDataModel model) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("deviceId", model.getDevice_id());
        if (model.getUser_id() != null) jsonMap.put("memberNo", model.getUser_id());
        jsonMap.put("email", model.getEmail());
        if (model.getName() != null) jsonMap.put("name", model.getName());
        if (model.getBirth() != null) jsonMap.put("birthDate", model.getBirth());
        if (model.getPhone() != null) jsonMap.put("phoneNumber", model.getPhone());
        jsonMap.put("snsTypeCode", model.getType());
        jsonMap.put("pushToken", model.getToken_push());
        jsonMap.put("isAgreeCommercial", model.isCommercialNoti());
        if (model.getCi() != null) jsonMap.put("ci", model.getCi());
        return jsonMap;
    }
}
