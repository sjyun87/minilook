package com.minilook.minilook.ui.preorder_detail;

import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.preorder_detail.adapter.PreOrderDetailArguments;

public class PreOrderDetailPresenterImpl extends BasePresenterImpl implements PreOrderDetailPresenter {

    private final View view;

    public PreOrderDetailPresenterImpl(PreOrderDetailArguments args) {
        view = args.getView();
    }

    @Override public void onCreate() {
    }
}