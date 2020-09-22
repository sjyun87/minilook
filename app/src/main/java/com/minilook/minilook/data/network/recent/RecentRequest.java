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

    public Single<BaseDataModel> getRecentProducts(int recent_id, int rows) {
        int user_id = App.getInstance().getMemberNo();
        return getApi().getRecentProducts(user_id, createRequestBody(parseToJson(recent_id, rows)));
    }

    private Map<String, Object> parseToJson(int recent_id, int rows) {
        Map<String, Object> jsonMap = new HashMap<>();
        if (recent_id != -1) jsonMap.put("lastRecentNo", recent_id);
        jsonMap.put("pageSize", rows);
        return jsonMap;
    }

    public Single<BaseDataModel> deleteRecent(int recent_id) {
        int user_id = App.getInstance().getMemberNo();
        return getApi().deleteRecent(user_id, recent_id);
    }
}
