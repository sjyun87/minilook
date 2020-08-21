package com.minilook.minilook.ui.order_cancel;

import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.order_cancel.di.OrderCancelArguments;

public class OrderCancelPresenterImpl extends BasePresenterImpl implements OrderCancelPresenter {

    private final View view;

    public OrderCancelPresenterImpl(OrderCancelArguments args) {
        view = args.getView();
    }

    @Override public void onCreate() {
        view.setupRecyclerView();
    }
}