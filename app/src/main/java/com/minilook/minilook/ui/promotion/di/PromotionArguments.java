package com.minilook.minilook.ui.promotion.di;

import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.promotion.PromotionPresenter;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class PromotionArguments {
  private final PromotionPresenter.View view;
  private final int promotionId;
  private final BaseAdapterDataModel<ProductDataModel> adapter;
}