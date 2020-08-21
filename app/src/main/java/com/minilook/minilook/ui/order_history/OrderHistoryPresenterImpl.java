package com.minilook.minilook.ui.order_history;

import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.order_history.di.OrderHistoryArguments;

public class OrderHistoryPresenterImpl extends BasePresenterImpl implements OrderHistoryPresenter {

    private final View view;

    public OrderHistoryPresenterImpl(OrderHistoryArguments args) {
        view = args.getView();
    }

    @Override public void onCreate() {
        view.setupRecyclerView();
    }
}