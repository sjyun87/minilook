package com.minilook.minilook.ui.product_detail;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import com.minilook.minilook.data.model.order.OrderOptionDataModel;
import com.minilook.minilook.data.model.product.ProductOptionDataModel;
import com.minilook.minilook.data.model.product.ProductStockModel;
import java.util.List;

public interface ProductDetailPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onTabClick(int position);

    void onBuyClick();

    void onBrandClick();

    void onExpandClick();

    void onShoppingBagClick(List<OrderOptionDataModel> goodsData);

    interface View {

        void setupProductImageViewPager();

        void productImageRefresh();

        void setupTabLayout();

        void setupWebView();

        void setupRelatedProductRecyclerView();

        void relatedProductRefresh();

        void setupReviewRecyclerView();

        void reviewRefresh();

        void showRelatedPanel();

        void hideRelatedPanel();

        void setupBrandName(String text);

        void setupProductName(String text);

        void addColorView(ProductStockModel model);

        void addSizeView(ProductStockModel model);

        void setupPriceOrigin(String text);

        void showPriceOrigin();

        void hidePriceOrigin();

        void setupDiscountPercent(int percent);

        void showDiscountPercent();

        void hideDiscountPercent();

        void setupPrice(String text);

        void setupPoint(int point);

        void setupShipping(int price);

        void setupShippingConditional(int price);

        void showShippingConditional();

        void hideShippingConditional();

        void scrollToProductInfo();

        void scrollToReview();

        void scrollToQuestion();

        void scrollToShippingNRefund();

        void setupProductDetail(String htmlText);

        void setupReviewCount(String text);

        void showReviewContentsPanel();

        void setupQuestionCount(String text);

        void setupOptionSelector(int price, List<ProductOptionDataModel> options);

        void showOptionSelector();

        void hideOptionSelector();

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

        void showDisplayLabel(String label);

        void disableBuyButton(String label);

        void setupPriceOriginNoDisplayColor();

        void setupDiscountPercentNoDisplayColor();

        void setupPriceNoDisplayColor();

        void hideScrap();

        void navigateToBrandDetail(int brand_id);

        void navigateToShoppingBag();
    }
}
