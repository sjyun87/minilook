package com.minilook.minilook.ui.order_exchange_n_return.di;

import com.minilook.minilook.data.model.order.CodeDataModel;
import com.minilook.minilook.data.model.order.OrderGoodsDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.order_exchange_n_return.OrderExchangeNReturnPresenter;
import com.minilook.minilook.ui.order_exchange_n_return.adapter.ExchangeNReturnReasonAdapter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class OrderExchangeNReturnArguments {
    private final OrderExchangeNReturnPresenter.View view;
    private final OrderGoodsDataModel data;
    private final BaseAdapterDataModel<CodeDataModel> typeAdapter;
    private final BaseAdapterDataModel<CodeDataModel> reasonAdapter;
}