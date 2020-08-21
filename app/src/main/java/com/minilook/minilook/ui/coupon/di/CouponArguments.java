package com.minilook.minilook.ui.coupon.di;

import com.minilook.minilook.ui.coupon.CouponPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class CouponArguments {
    private final CouponPresenter.View view;
}