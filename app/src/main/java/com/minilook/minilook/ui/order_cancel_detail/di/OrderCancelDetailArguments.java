package com.minilook.minilook.ui.order_cancel_detail.di;

import com.minilook.minilook.ui.order_cancel_detail.OrderCancelDetailPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class OrderCancelDetailArguments {
    private final OrderCancelDetailPresenter.View view;
}