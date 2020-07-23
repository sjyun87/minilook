package com.minilook.minilook.ui.search_filter;

import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.search_filter.adapter.SearchFilterArguments;

public class SearchFilterPresenterImpl extends BasePresenterImpl implements SearchFilterPresenter {

    private final View view;

    public SearchFilterPresenterImpl(SearchFilterArguments args) {
        view = args.getView();
    }

    @Override public void onCreate() {
    }
}