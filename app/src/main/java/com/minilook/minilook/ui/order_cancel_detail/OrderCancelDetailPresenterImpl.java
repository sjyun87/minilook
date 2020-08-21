package com.minilook.minilook.ui.order_cancel_detail;

import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.order_cancel_detail.di.OrderCancelDetailArguments;

public class OrderCancelDetailPresenterImpl extends BasePresenterImpl implements OrderCancelDetailPresenter {

    private final View view;

    public OrderCancelDetailPresenterImpl(OrderCancelDetailArguments args) {
        view = args.getView();
    }

    @Override public void onCreate() {
        view.setupRecyclerView();
    }
}