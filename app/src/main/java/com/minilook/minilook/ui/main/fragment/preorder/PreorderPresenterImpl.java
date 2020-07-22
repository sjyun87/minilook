package com.minilook.minilook.ui.main.fragment.preorder;

import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.main.fragment.preorder.di.PreorderArguments;

public class PreorderPresenterImpl extends BasePresenterImpl implements PreorderPresenter {

    private final View view;

    public PreorderPresenterImpl(PreorderArguments args) {
        view = args.getView();
    }

    @Override public void onCreate() {
    }
}