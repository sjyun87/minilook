package com.minilook.minilook.ui.brand.adapter;

import com.minilook.minilook.ui.brand.BrandPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class BrandArguments {
    private final BrandPresenter.View view;
}