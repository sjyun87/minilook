package com.minilook.minilook.data.network.challenge;

import android.text.TextUtils;
import com.minilook.minilook.App;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.member.MemberDataModel;
import com.minilook.minilook.data.network.base.BaseRequest;
import io.reactivex.rxjava3.core.Single;
import java.util.HashMap;
import java.util.Map;
import okhttp3.RequestBody;

public class ChallengeRequest extends BaseRequest<ChallengeService> {

    @Override protected Class<ChallengeService> getService() {
        return ChallengeService.class;
    }

    public Single<BaseDataModel> getOpenChallenge(int page, int rows) {
        return getApi().getOpenChallenge(createGetChallengeData(page, rows));
    }

    public Single<BaseDataModel> getComingChallenge(int page, int rows) {
        return getApi().getComingChallenge(createGetChallengeData(page, rows));
    }

    public Single<BaseDataModel> getCloseChallenge(int rows) {
        return getApi().getCloseChallenge(createGetChallengeData(1, rows));
    }

    private RequestBody createGetChallengeData(int page, int rows) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("current", page);
        jsonMap.put("pageSize", rows);
        if (App.getInstance().isLogin()) jsonMap.put("memberNo", App.getInstance().getMemberNo());
        return createRequestBody(jsonMap);
    }

    public Single<BaseDataModel> getChallengeDetail(int challengeNo) {
        return getApi().getChallengeDetail(challengeNo, createGetChallengeDetailData());
    }

    private RequestBody createGetChallengeDetailData() {
        Map<String, Object> jsonMap = new HashMap<>();
        if (App.getInstance().isLogin()) jsonMap.put("memberNo", App.getInstance().getMemberNo());
        return createRequestBody(jsonMap);
    }

    public Single<BaseDataModel> getMemberData() {
        return getApi().getMemberData(App.getInstance().getMemberNo());
    }

    public Single<BaseDataModel> enterChallenge(int challengeNo, MemberDataModel memberData) {
        return getApi().enterChallenge(challengeNo, App.getInstance().getMemberNo(),
            createChallengeEnterData(memberData));
    }

    private RequestBody createChallengeEnterData(MemberDataModel memberData) {
        Map<String, Object> jsonMap = new HashMap<>();
        Map<String, Object> contactMap = new HashMap<>();
        contactMap.put("ci", memberData.getCi());
        contactMap.put("name", memberData.getName());
        contactMap.put("phone", memberData.getPhone());
        jsonMap.put("contact", contactMap);
        jsonMap.put("isAgreeTermsOfService", true);
        Map<String, Object> snsMap = new HashMap<>();
        if (!TextUtils.isEmpty(memberData.getInstagram())) snsMap.put("instagramAccount", memberData.getInstagram());
        if (!TextUtils.isEmpty(memberData.getBlog())) snsMap.put("blogAddress", memberData.getBlog());
        jsonMap.put("memberSns", snsMap);
        return createRequestBody(jsonMap);
    }
}
