package com.minilook.minilook.data.network.search;

import com.minilook.minilook.data.model.search.SearchDataModel;
import com.minilook.minilook.data.network.base.BaseRequest;

import io.reactivex.rxjava3.core.Single;

public class SearchRequest extends BaseRequest<SearchService> {

    @Override protected Class<SearchService> getService() {
        return SearchService.class;
    }

    public Single<SearchDataModel> getSearchModule() {
        return getApi().getSearchModule("Search");
    }
}
