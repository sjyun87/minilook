package com.minilook.minilook.ui.payment;

import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.payment.di.PaymentArguments;

public class PaymentPresenterImpl extends BasePresenterImpl implements PaymentPresenter {

    private final View view;

    public PaymentPresenterImpl(PaymentArguments args) {
        view = args.getView();
    }

    @Override public void onCreate() {
        view.setupWebView();
    }
}