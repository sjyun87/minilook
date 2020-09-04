package com.minilook.minilook.ui.base.listener;

public interface OnScrapListener {

    void onProductScrap(boolean isScrap, int product_id);

    void onBrandScrap(boolean isScrap, int brand_id);
}
