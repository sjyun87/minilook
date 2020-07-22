package com.minilook.minilook.ui.main.fragment.ipage;

import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.main.fragment.ipage.di.IpageArguments;

public class IpagePresenterImpl extends BasePresenterImpl implements IpagePresenter {

    private final View view;

    public IpagePresenterImpl(IpageArguments args) {
        view = args.getView();
    }

    @Override public void onCreate() {
    }
}