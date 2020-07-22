package com.minilook.minilook.ui.brand_detail.di;

import com.minilook.minilook.ui.brand_detail.BrandDetailPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class BrandDetailArguments {
  private final BrandDetailPresenter.View view;
  private final int brandId;
}