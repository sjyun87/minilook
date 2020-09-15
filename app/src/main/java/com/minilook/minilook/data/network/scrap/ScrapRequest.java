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
        int user_id = App.getInstance().getMemberId();
        return getApi().getScrapProducts(user_id, createRequestBody(parseToScrapbookJson(page, rows)));
    }

    public Single<BaseDataModel> getScrapBrands(int page, int rows) {
        int user_id = App.getInstance().getMemberId();
        return getApi().getScrapBrands(user_id, createRequestBody(parseToScrapbookJson(page, rows)));
    }

    private Map<String, Object> parseToScrapbookJson(int page, int rows) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("current", page);
        jsonMap.put("pageSize", rows);
        return jsonMap;
    }

    public Single<BaseDataModel> updateProductScrap(boolean isScrap, int product_id) {
        if (isScrap) {
            return getApi().productScrapOn(createRequestBody(parseToProductScrapJson(product_id)));
        } else {
            return getApi().productScrapOff(createRequestBody(parseToProductScrapJson(product_id)));
        }
    }

    private Map<String, Object> parseToProductScrapJson(int product_id) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("memberNo", App.getInstance().getMemberId());
        jsonMap.put("productNo", product_id);
        return jsonMap;
    }

    public Single<BaseDataModel> updateBrandScrap(boolean isScrap, int brand_id) {
        if (isScrap) {
            return getApi().brandScrapOn(createRequestBody(parseToBrandScrapJson(brand_id)));
        } else {
            return getApi().brandScrapOff(createRequestBody(parseToBrandScrapJson(brand_id)));
        }
    }

    private Map<String, Object> parseToBrandScrapJson(int brand_id) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("memberNo", App.getInstance().getMemberId());
        jsonMap.put("brandNo", brand_id);
        return jsonMap;
    }
}
