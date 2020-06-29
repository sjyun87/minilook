package com.minilook.minilook.ui.detail;

import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.detail.di.DetailArguments;

public class DetailPresenterImpl extends BasePresenterImpl implements DetailPresenter {

    private final View view;
    private final String webUrl;

    public DetailPresenterImpl(DetailArguments args) {
        view = args.getView();
        webUrl = args.getWebUrl();
    }

    @Override public void onCreate() {
        view.setupWebView();
        view.loadUrl(webUrl);
    }
}