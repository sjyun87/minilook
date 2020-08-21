package com.minilook.minilook.ui.order_detail;

import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.order_detail.di.OrderDetailArguments;

public class OrderDetailPresenterImpl extends BasePresenterImpl implements OrderDetailPresenter {

    private final View view;

    public OrderDetailPresenterImpl(OrderDetailArguments args) {
        view = args.getView();
    }

    @Override public void onCreate() {
        view.setupRecyclerView();
    }
}