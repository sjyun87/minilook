package com.minilook.minilook.ui.order_cancel.di;

import com.minilook.minilook.data.model.order.OrderCancelDataModel;
import com.minilook.minilook.data.model.order.OrderProductDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.order_cancel.OrderCancelPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class OrderCancelArguments {
    private final OrderCancelPresenter.View view;
    private final OrderCancelDataModel orderData;
    private final BaseAdapterDataModel<OrderProductDataModel> adapter;
}