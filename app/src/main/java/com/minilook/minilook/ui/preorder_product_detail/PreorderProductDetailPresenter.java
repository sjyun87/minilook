package com.minilook.minilook.ui.preorder_product_detail;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import com.minilook.minilook.data.model.product.ProductStockDataModel;

public interface PreorderProductDetailPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onExpandClick();

    interface View {

        void setTitle(String title);

        void setupViewPager();

        void imageRefresh();

        void setProductIndex(String title);

        void setProductName(String name);

        void addSizeView(ProductStockDataModel model);

        void addColorView(ProductStockDataModel model);

        void setupPriceOrigin(String text);

        void showPriceOrigin();

        void hidePriceOrigin();

        void setupDiscountPercent(int percent);

        void showDiscountPercent();

        void hideDiscountPercent();

        void setupPrice(String text);

        void setupWebView();

        void setupProductDetail(String url);

        void setupInfoStyleNo(String text);

        void setupInfoKcAuth(String text);

        void setupInfoWeight(String text);

        void setupInfoColor(String text);

        void setupInfoMaterial(String text);

        void setupInfoAge(String text);

        void setupInfoReleaseDate(String text);

        void setupInfoManufacturer(String text);

        void setupInfoCountry(String text);

        void setupInfoCaution(String text);

        void setupInfoWarranty(String text);

        void setupInfoDamage(String text);

        void setupInfoServiceCenter(String text);

        void expandInfoMorePanel();

        void collapseInfoMorePanel();
    }
}
