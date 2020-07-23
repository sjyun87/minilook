package com.minilook.minilook.ui.product_bridge;

import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.product_bridge.di.ProductBridgeArguments;

public class ProductBridgePresenterImpl extends BasePresenterImpl implements ProductBridgePresenter {

    private final View view;

    public ProductBridgePresenterImpl(ProductBridgeArguments args) {
        view = args.getView();
    }

    @Override public void onCreate() {
    }
}