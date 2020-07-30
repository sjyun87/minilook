package com.minilook.minilook.ui.brand;

import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.brand.adapter.BrandArguments;

public class BrandPresenterImpl extends BasePresenterImpl implements BrandPresenter {

    private final View view;

    public BrandPresenterImpl(BrandArguments args) {
        view = args.getView();
    }

    @Override public void onCreate() {
    }
}