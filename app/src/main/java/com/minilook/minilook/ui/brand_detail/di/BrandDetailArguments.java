package com.minilook.minilook.ui.brand_detail.di;

import com.minilook.minilook.data.model.common.CodeDataModel;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.brand_detail.BrandDetailPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class BrandDetailArguments {
    private final BrandDetailPresenter.View view;
    private final int brandNo;
    private final BaseAdapterDataModel<String> styleAdapter;
    private final BaseAdapterDataModel<CodeDataModel> sortAdapter;
    private final BaseAdapterDataModel<ProductDataModel> productAdapter;
}