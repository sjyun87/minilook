package com.minilook.minilook.data.network.recent;

import com.minilook.minilook.App;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.network.base.BaseRequest;
import io.reactivex.rxjava3.core.Single;
import java.util.HashMap;
import java.util.Map;
import okhttp3.RequestBody;

public class RecentRequest extends BaseRequest<RecentService> {

    @Override protected Class<RecentService> getService() {
        return RecentService.class;
    }

    public Single<BaseDataModel> getRecentProducts(int recentNo, int rows) {
        int memberNo = App.getInstance().getMemberNo();
        return getApi().getRecentProducts(memberNo, createRecentProductsData(recentNo, rows));
    }

    private RequestBody createRecentProductsData(int recentNo, int rows) {
        Map<String, Object> jsonMap = new HashMap<>();
        if (recentNo != -1) jsonMap.put("lastRecentNo", recentNo);
        jsonMap.put("pageSize", rows);
        return createRequestBody(jsonMap);
    }

    public Single<BaseDataModel> deleteRecent(int recentNo) {
        int memberNo = App.getInstance().getMemberNo();
        return getApi().deleteRecent(memberNo, recentNo);
    }
}
