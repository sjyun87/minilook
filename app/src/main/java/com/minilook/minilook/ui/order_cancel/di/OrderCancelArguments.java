package com.minilook.minilook.ui.order_cancel.di;

import com.minilook.minilook.ui.order_cancel.OrderCancelPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class OrderCancelArguments {
    private final OrderCancelPresenter.View view;
}