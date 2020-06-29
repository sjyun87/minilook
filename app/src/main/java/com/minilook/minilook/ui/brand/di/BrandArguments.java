package com.minilook.minilook.ui.brand.di;

import com.minilook.minilook.ui.brand.BrandPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class BrandArguments {
  private final BrandPresenter.View view;
  private final int brandId;
}