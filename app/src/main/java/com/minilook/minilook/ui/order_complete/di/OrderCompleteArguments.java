package com.minilook.minilook.ui.order_complete.di;

import com.minilook.minilook.ui.order_complete.OrderCompletePresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class OrderCompleteArguments {
    private final OrderCompletePresenter.View view;
}