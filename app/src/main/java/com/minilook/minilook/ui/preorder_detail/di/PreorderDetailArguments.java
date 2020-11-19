package com.minilook.minilook.ui.preorder_detail.di;

import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.preorder_detail.PreorderDetailPresenter;
import com.minilook.minilook.util.DynamicLinkManager;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class PreorderDetailArguments {
    private final PreorderDetailPresenter.View view;
    private final int preorderNo;
    private final BaseAdapterDataModel<String> imageAdapter;
    private final BaseAdapterDataModel<ProductDataModel> productAdapter;
    private final DynamicLinkManager dynamicLinkManager;
}