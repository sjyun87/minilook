package com.minilook.minilook.data.network.scrap;

import com.minilook.minilook.App;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.network.base.BaseRequest;
import io.reactivex.rxjava3.core.Single;
import java.util.HashMap;
import java.util.Map;

public class ScrapRequest extends BaseRequest<ScrapService> {

    @Override protected Class<ScrapService> getService() {
        return ScrapService.class;
    }

    public Single<BaseDataModel> getScrapProducts(int page, int rows) {
        int user_id = App.getInstance().getUserId();
        return getApi().getScrapProducts(user_id, createRequestBody(parseToScrapbookJson(page, rows)));
    }

    public Single<BaseDataModel> getScrapBrands(int page, int rows) {
        int user_id = App.getInstance().getUserId();
        return getApi().getScrapBrands(user_id, createRequestBody(parseToScrapbookJson(page, rows)));
    }

    private Map<String, Object> parseToScrapbookJson(int page, int rows) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("current", page);
        jsonMap.put("pageSize", rows);
        return jsonMap;
    }

    public Single<BaseDataModel> updateScrap(boolean isScrap, int product_id) {
        if (isScrap) {
            return getApi().checkScrap(createRequestBody(parseToScrapJson(product_id)));
        } else {
            return getApi().uncheckScrap(createRequestBody(parseToScrapJson(product_id)));
        }
    }

    private Map<String, Object> parseToScrapJson(int product_id) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("memberNo", App.getInstance().getUserId());
        jsonMap.put("productNo", product_id);
        return jsonMap;
    }
}
