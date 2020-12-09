package com.minilook.minilook.ui.preorder_product_detail.di;

import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.preorder_product_detail.PreorderProductDetailPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class PreorderProductDetailArguments {
    private final PreorderProductDetailPresenter.View view;
    private final String title;
    private final int preorderNo;
    private final int productNo;
    private final BaseAdapterDataModel<String> imageAdapter;
}