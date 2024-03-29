package com.minilook.minilook.ui.product_detail.di;

import com.minilook.minilook.data.model.common.ImageDataModel;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.data.model.review.ReviewDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.product_detail.ProductDetailPresenter;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class ProductDetailArguments {
    private final ProductDetailPresenter.View view;
    private final int productNo;
    private final BaseAdapterDataModel<String> productImageAdapter;
    private final BaseAdapterDataModel<ReviewDataModel> reviewAdapter;
    private final BaseAdapterDataModel<ImageDataModel> photoReviewAdapter;
    private final BaseAdapterDataModel<ProductDataModel> relatedProductAdapter;
}