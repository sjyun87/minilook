package com.minilook.minilook.ui.webview;

import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.webview.di.WebViewArguments;

public class WebViewPresenterImpl extends BasePresenterImpl implements WebViewPresenter {

    private final View view;
    private final String url;

    public WebViewPresenterImpl(WebViewArguments args) {
        view = args.getView();
        url = args.getUrl();
    }

    @Override public void onCreate() {
        view.setupWebView();
        view.loadURL(url);
    }
}