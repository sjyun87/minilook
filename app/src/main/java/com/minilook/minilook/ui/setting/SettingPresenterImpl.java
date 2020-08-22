package com.minilook.minilook.ui.setting;

import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.setting.di.SettingArguments;

public class SettingPresenterImpl extends BasePresenterImpl implements SettingPresenter {

    private final View view;

    public SettingPresenterImpl(SettingArguments args) {
        view = args.getView();
    }

    @Override public void onCreate() {
    }
}