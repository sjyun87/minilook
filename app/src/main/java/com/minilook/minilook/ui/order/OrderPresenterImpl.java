package com.minilook.minilook.ui.order;

import com.minilook.minilook.App;
import com.minilook.minilook.data.model.order.OrderBrandDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.order.di.OrderArguments;
import java.util.List;

public class OrderPresenterImpl extends BasePresenterImpl implements OrderPresenter {

    private final View view;
    private final List<OrderBrandDataModel> orderItem;

    public OrderPresenterImpl(OrderArguments args) {
        view = args.getView();
        orderItem = App.getInstance().getOrderItems();
        App.getInstance().getOrderItems().clear();
    }

    @Override public void onCreate() {
        view.setupRecyclerView();
    }
}