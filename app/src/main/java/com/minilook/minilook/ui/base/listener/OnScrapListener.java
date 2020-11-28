package com.minilook.minilook.ui.base.listener;

import com.minilook.minilook.data.model.brand.BrandDataModel;
import com.minilook.minilook.data.model.product.ProductDataModel;

public interface OnScrapListener {

    void onProductScrap(ProductDataModel data);

    void onBrandScrap(BrandDataModel data);
}
