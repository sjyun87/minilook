package com.minilook.minilook.ui.payment.di;

import com.minilook.minilook.ui.payment.PaymentPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class PaymentArguments {
    private final PaymentPresenter.View view;
}