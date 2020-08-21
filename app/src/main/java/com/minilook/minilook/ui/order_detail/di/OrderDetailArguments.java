package com.minilook.minilook.ui.order_detail.di;

import com.minilook.minilook.ui.order_detail.OrderDetailPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class OrderDetailArguments {
    private final OrderDetailPresenter.View view;
}