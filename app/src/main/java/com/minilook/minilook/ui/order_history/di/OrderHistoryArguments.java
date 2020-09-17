package com.minilook.minilook.ui.order_history.di;

import com.minilook.minilook.data.model.order.OrderHistoryDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.order_history.OrderHistoryPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class OrderHistoryArguments {
    private final OrderHistoryPresenter.View view;
    private final BaseAdapterDataModel<OrderHistoryDataModel> adapter;
}