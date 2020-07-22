package com.minilook.minilook.ui.product_detail;

import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.product_detail.di.ProductDetailArguments;

public class ProductDetailPresenterImpl extends BasePresenterImpl implements ProductDetailPresenter {

    private final View view;
    private final String webUrl;

    public ProductDetailPresenterImpl(ProductDetailArguments args) {
        view = args.getView();
        webUrl = args.getWebUrl();
    }

    @Override public void onCreate() {
        view.setupWebView();
        view.loadUrl(webUrl);
    }
}