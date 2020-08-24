package com.minilook.minilook.ui.brand_info.di;

import com.minilook.minilook.ui.brand_info.BrandInfoPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class BrandInfoArguments {
    private final BrandInfoPresenter.View view;
    private final int brand_id;
}