package com.minilook.minilook.data.network.search;

import com.minilook.minilook.App;
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

    public Single<BaseDataModel> getProducts(int page, int rows, SearchOptionDataModel options) {
        return getApi().getProducts(createRequestBody(createGetProductsData(page, rows, options)));
    }

    private Map<String, Object> createGetProductsData(int page, int rows, SearchOptionDataModel options) {
        Map<String, Object> jsonMap = new HashMap<>();
        if (App.getInstance().isLogin()) jsonMap.put("memberNo", App.getInstance().getMemberNo());
        jsonMap.put("current", page);
        jsonMap.put("pageSize", rows);
        jsonMap.put("productOrderByCode", options.getSortCode());
        if (options.getBrandNo() > 0) jsonMap.put("brandNo", options.getBrandNo());
        if (options.getGenderCode() != null) jsonMap.put("genderCode", options.getGenderCode());
        if (options.getAge() > 0) jsonMap.put("age", options.getAge());
        if (options.getCategoryCode() != null) jsonMap.put("categoryCode", options.getCategoryCode());
        if (options.getCategoryDerailCode() != null) {
            jsonMap.put("categoryDetailCode", options.getCategoryDerailCode());
        }
        if (options.getMinPrice() > 0) jsonMap.put("startPrice", options.getMinPrice());
        if (options.getMaxPrice() > 0) jsonMap.put("endPrice", options.getMaxPrice());
        if (options.getColorCodes() != null && options.getColorCodes().size() > 0) {
            jsonMap.put("colorCode", options.getColorCodes());
        }
        if (options.getStyleCodes() != null && options.getStyleCodes().size() > 0) {
            jsonMap.put("styleCode", options.getStyleCodes());
        }
        jsonMap.put("discount", options.isDiscount());
        jsonMap.put("outOfStock", options.isStock());
        return jsonMap;
    }

    public Single<BaseDataModel> getFilterOptions(String categoryCode) {
        return getApi().getFilterOptions(categoryCode);
    }

    public Single<BaseDataModel> getFilterOptions() {
        return getApi().getFilterOptions();
    }

    public Single<BaseDataModel> getRecommendKeywords() {
        return getApi().getRecommendKeywords();
    }
}
