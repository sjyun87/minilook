package com.minilook.minilook.data.network.search;

import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.search.SearchOptionDataModel;
import com.minilook.minilook.data.network.base.BaseRequest;

import io.reactivex.rxjava3.core.Single;
import java.util.HashMap;
import java.util.Map;

public class SearchRequest extends BaseRequest<SearchService> {

    @Override protected Class<SearchService> getService() {
        return SearchService.class;
    }

    public Single<BaseDataModel> getProducts(SearchOptionDataModel options) {
        return getApi().getProducts(createRequestBody(parseToJson(options)));
    }

    public Single<BaseDataModel> getFilterOptions() {
        return getApi().getFilterOptions();
    }

    private Map<String, Object> parseToJson(SearchOptionDataModel options) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("brandNo", options.getBrand_id());
        jsonMap.put("current", options.getPage());
        jsonMap.put("pageSize", options.getRow());
        return jsonMap;
    }
}
