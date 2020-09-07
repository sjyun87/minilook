package com.minilook.minilook.ui.product_detail.di;

import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.data.model.review.ReviewDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.product.adapter.ProductAdapter;
import com.minilook.minilook.ui.product_detail.ProductDetailPresenter;

import com.minilook.minilook.ui.product_detail.adapter.ProductDetailImageAdapter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class ProductDetailArguments {
    private final ProductDetailPresenter.View view;
    private final int id;
    private final BaseAdapterDataModel<String> productImageAdapter;
    private final BaseAdapterDataModel<ReviewDataModel> reviewAdapter;
    private final BaseAdapterDataModel<ProductDataModel> relatedProductAdapter;
}