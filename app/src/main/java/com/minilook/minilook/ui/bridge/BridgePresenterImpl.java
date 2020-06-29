package com.minilook.minilook.ui.bridge;

import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.bridge.di.BridgeArguments;

public class BridgePresenterImpl extends BasePresenterImpl implements BridgePresenter {

    private final View view;

    public BridgePresenterImpl(BridgeArguments args) {
        view = args.getView();
    }

    @Override public void onCreate() {
    }
}