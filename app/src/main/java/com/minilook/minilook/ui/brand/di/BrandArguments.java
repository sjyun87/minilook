package com.minilook.minilook.ui.brand.di;

import com.minilook.minilook.data.model.brand.BrandDataModel;
import com.minilook.minilook.data.model.common.StyleDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.brand.BrandPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class BrandArguments {
    private final BrandPresenter.View view;
    private final BaseAdapterDataModel<StyleDataModel> styleAdapter;
    private final BaseAdapterDataModel<BrandDataModel> brandAdapter;
}