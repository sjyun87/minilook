package com.minilook.minilook.data.network.search;

import com.minilook.minilook.App;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.search.SearchOptionDataModel;
import com.minilook.minilook.data.network.base.BaseRequest;
import io.reactivex.rxjava3.core.Single;
import java.util.HashMap;
import java.util.Map;
import timber.log.Timber;

public class SearchRequest extends BaseRequest<SearchService> {

    @Override protected Class<SearchService> getService() {
        return SearchService.class;
    }

    public Single<BaseDataModel> getProducts(int page, int rows, SearchOptionDataModel options) {
        return getApi().getProducts(createRequestBody(parseToJson(page, rows, options)));
    }

    public Single<BaseDataModel> getFilterOptions(String category_code) {
        return getApi().getFilterOptions(category_code);
    }

    private Map<String, Object> parseToJson(int page, int rows, SearchOptionDataModel options) {
        Map<String, Object> jsonMap = new HashMap<>();
        if (App.getInstance().isLogin()) jsonMap.put("memberNo", App.getInstance().getUserId());
        jsonMap.put("current", page);
        jsonMap.put("pageSize", rows);
        jsonMap.put("productOrderByCode", options.getOrder());
        if (options.getBrand_id() != 0) jsonMap.put("brandNo", options.getBrand_id());
        if (options.getGender_code() != null) jsonMap.put("genderCode", options.getGender_code());
        if (options.getAge() != -1) jsonMap.put("age", options.getAge());
        if (options.getCategory_code() != null) jsonMap.put("categoryCode", options.getCategory_code());
        if (options.getCategory_derail_code() != null) jsonMap.put("categoryDetailCode", options.getCategory_derail_code());
        if (options.getType() != -1) jsonMap.put("sizeType", options.getType());
        if (options.getPrice_min() != -1) jsonMap.put("startPrice", options.getPrice_min());
        if (options.getPrice_max() != -1) jsonMap.put("endPrice", options.getPrice_max());
        if (options.getColor_codes() != null && options.getColor_codes().size() > 0) {
            jsonMap.put("colorCode", options.getColor_codes());
        }
        if (options.getStyle_codes() != null && options.getStyle_codes().size() > 0) {
            jsonMap.put("styleCode", options.getStyle_codes());
        }
        jsonMap.put("discount", options.isDiscount());
        jsonMap.put("outOfStock", options.isStock());
        Timber.e(jsonMap.toString());
        return jsonMap;
    }
}
