package com.minilook.minilook.ui.shipping;

import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.shipping.di.ShippingArguments;

public class ShippingPresenterImpl extends BasePresenterImpl implements ShippingPresenter {

    private final View view;

    public ShippingPresenterImpl(ShippingArguments args) {
        view = args.getView();
    }

    @Override public void onCreate() {
        view.setupRecyclerView();
    }
}