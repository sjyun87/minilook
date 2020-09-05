package com.minilook.minilook.ui.search_zip;

import com.minilook.minilook.data.common.URLKeys;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.search_zip.di.SearchZipArguments;

public class SearchZipPresenterImpl extends BasePresenterImpl implements SearchZipPresenter {

    private final View view;

    public SearchZipPresenterImpl(SearchZipArguments args) {
        view = args.getView();
    }

    @Override public void onCreate() {
        view.setupWebView();
        view.loadURL(URLKeys.URL_SEARCH_ZIP);
    }
}