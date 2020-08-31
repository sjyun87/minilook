package com.minilook.minilook.ui.search_address;

import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.search_address.di.SearchAddressArguments;

public class SearchAddressPresenterImpl extends BasePresenterImpl implements SearchAddressPresenter {

    private final View view;

    public SearchAddressPresenterImpl(SearchAddressArguments args) {
        view = args.getView();
    }

    @Override public void onCreate() {
        view.setupWebView();
    }
}