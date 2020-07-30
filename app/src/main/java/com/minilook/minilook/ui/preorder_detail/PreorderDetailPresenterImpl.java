package com.minilook.minilook.ui.preorder_detail;

import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.preorder_detail.adapter.PreorderDetailArguments;

public class PreorderDetailPresenterImpl extends BasePresenterImpl implements PreorderDetailPresenter {

    private final View view;

    public PreorderDetailPresenterImpl(PreorderDetailArguments args) {
        view = args.getView();
    }

    @Override public void onCreate() {
    }
}