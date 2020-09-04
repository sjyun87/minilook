package com.minilook.minilook.ui.base.listener;

import com.minilook.minilook.data.model.brand.BrandDataModel;
import com.minilook.minilook.data.model.product.ProductDataModel;

public interface OnScrapListener {

    void onProductScrap(boolean isScrap, ProductDataModel product);

    void onBrandScrap(boolean isScrap, BrandDataModel brand);
}
