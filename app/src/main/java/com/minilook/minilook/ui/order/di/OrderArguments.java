package com.minilook.minilook.ui.order.di;

import com.minilook.minilook.ui.order.OrderPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class OrderArguments {
    private final OrderPresenter.View view;
}