package com.minilook.minilook.ui.order_complete;

import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.order_complete.di.OrderCompleteArguments;

public class OrderCompletePresenterImpl extends BasePresenterImpl implements OrderCompletePresenter {

    private final View view;

    public OrderCompletePresenterImpl(OrderCompleteArguments args) {
        view = args.getView();
    }

    @Override public void onOrderHistoryClick() {
        view.navigateToOrderHistory();
        view.finish();
    }

    @Override public void onShoppingClick() {
        view.navigateToMain();
        view.finish();
    }
}