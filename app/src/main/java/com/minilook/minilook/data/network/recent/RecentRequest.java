package com.minilook.minilook.data.network.recent;

import com.minilook.minilook.App;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.network.base.BaseRequest;
import io.reactivex.rxjava3.core.Single;
import java.util.HashMap;
import java.util.Map;

public class RecentRequest extends BaseRequest<RecentService> {

    @Override protected Class<RecentService> getService() {
        return RecentService.class;
    }

    public Single<BaseDataModel> getRecentProducts(int recentNo, int rows) {
        //int user_id = App.getInstance().getUserId();
        int user_id = 85;
        return getApi().getRecentProducts(user_id, createRequestBody(parseToJson(recentNo, rows)));
    }

    private Map<String, Object> parseToJson(int recentNo, int rows) {
        Map<String, Object> jsonMap = new HashMap<>();
        if (recentNo != -1) jsonMap.put("lastRecentNo", recentNo);
        jsonMap.put("pageSize", rows);
        return jsonMap;
    }
}
