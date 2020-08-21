package com.minilook.minilook.ui.order_history.di;

import com.minilook.minilook.ui.order_history.OrderHistoryPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class OrderHistoryArguments {
    private final OrderHistoryPresenter.View view;
}