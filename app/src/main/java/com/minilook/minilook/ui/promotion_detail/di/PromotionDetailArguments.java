package com.minilook.minilook.ui.promotion_detail.di;

import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.data.model.promotion.PromotionDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.promotion_detail.PromotionDetailPresenter;

import com.minilook.minilook.util.DynamicLinkManager;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class PromotionDetailArguments {
  private final PromotionDetailPresenter.View view;
  private final int promotionId;
  private final BaseAdapterDataModel<ProductDataModel> productAdapter;
  private final BaseAdapterDataModel<PromotionDataModel> promotionAdapter;
  private final DynamicLinkManager dynamicLinkManager;
}