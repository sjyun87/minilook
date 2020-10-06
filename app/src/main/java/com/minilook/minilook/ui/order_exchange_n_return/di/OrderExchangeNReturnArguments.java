package com.minilook.minilook.ui.order_exchange_n_return.di;

import com.minilook.minilook.data.model.common.CodeDataModel;
import com.minilook.minilook.data.model.order.OrderProductDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.order_exchange_n_return.OrderExchangeNReturnPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class OrderExchangeNReturnArguments {
    private final OrderExchangeNReturnPresenter.View view;
    private final OrderProductDataModel data;
    private final BaseAdapterDataModel<CodeDataModel> typeAdapter;
    private final BaseAdapterDataModel<CodeDataModel> reasonAdapter;
}