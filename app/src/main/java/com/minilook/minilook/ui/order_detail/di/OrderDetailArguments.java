package com.minilook.minilook.ui.order_detail.di;

import com.minilook.minilook.data.model.order.OrderBrandDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.order_detail.OrderDetailPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class OrderDetailArguments {
    private final OrderDetailPresenter.View view;
    private final String orderNo;
    private final String receiptNo;
    private final BaseAdapterDataModel<OrderBrandDataModel> adapter;
}