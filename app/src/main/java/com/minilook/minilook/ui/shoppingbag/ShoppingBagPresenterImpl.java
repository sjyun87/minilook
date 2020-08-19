package com.minilook.minilook.ui.shoppingbag;

import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.shoppingbag.adapter.ShoppingBagArguments;

public class ShoppingBagPresenterImpl extends BasePresenterImpl implements ShoppingBagPresenter {

    private final View view;

    public ShoppingBagPresenterImpl(ShoppingBagArguments args) {
        view = args.getView();
    }

    @Override public void onCreate() {
    }
}