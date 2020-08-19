package com.minilook.minilook.ui.promotion_detail.di;

import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.promotion_detail.PromotionDetailPresenter;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class PromotionDetailArguments {
  private final PromotionDetailPresenter.View view;
  private final int promotionId;
  private final BaseAdapterDataModel<ProductDataModel> adapter;
}