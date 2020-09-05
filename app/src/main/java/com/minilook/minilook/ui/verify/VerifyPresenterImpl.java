package com.minilook.minilook.ui.verify;

import com.minilook.minilook.data.common.URLKeys;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.verify.di.VerifyArguments;

public class VerifyPresenterImpl extends BasePresenterImpl implements VerifyPresenter {

    private final View view;

    public VerifyPresenterImpl(VerifyArguments args) {
        view = args.getView();
    }

    @Override public void onCreate() {
        view.setupWebView();
        view.loadURL(URLKeys.URL_VERIFY);
    }
}