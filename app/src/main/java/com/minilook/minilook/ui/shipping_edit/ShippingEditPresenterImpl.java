package com.minilook.minilook.ui.shipping_edit;

import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.shipping_edit.di.ShippingEditArguments;

public class ShippingEditPresenterImpl extends BasePresenterImpl implements ShippingEditPresenter {

    private final View view;

    public ShippingEditPresenterImpl(ShippingEditArguments args) {
        view = args.getView();
    }

    @Override public void onCreate() {

    }
}