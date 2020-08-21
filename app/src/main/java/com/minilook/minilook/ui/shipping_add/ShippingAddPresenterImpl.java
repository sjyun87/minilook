package com.minilook.minilook.ui.shipping_add;

import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.shipping_add.di.ShippingAddArguments;

public class ShippingAddPresenterImpl extends BasePresenterImpl implements ShippingAddPresenter {

    private final View view;

    public ShippingAddPresenterImpl(ShippingAddArguments args) {
        view = args.getView();
    }

    @Override public void onCreate() {

    }
}