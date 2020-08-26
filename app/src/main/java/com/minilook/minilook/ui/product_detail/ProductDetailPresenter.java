package com.minilook.minilook.ui.product_detail;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import com.minilook.minilook.data.model.product.ProductColorDataModel;
import com.minilook.minilook.data.model.product.ProductSizeDataModel;
import com.minilook.minilook.data.model.product.ProductStockModel;
import java.util.List;

public interface ProductDetailPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onTabClick(int position);

    void onBuyClick();

    void onBrandClick();

    interface View {

        void setupProductImageViewPager();

        void productImageRefresh();

        void setupTabLayout();

        void setupWebView();

        void setupRelatedProductRecyclerView();

        void relatedProductRefresh();

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

        void setupDeliveryInfoTextView();

        void scrollToProductInfo();

        void scrollToReview();

        void scrollToQuestion();

        void scrollToShippingNRefund();

        void setupProductDetail(String htmlText);

        void setupReviewCount(String text);

        void setupQuestionCount(String text);

        void setupOptionSelector(List<ProductColorDataModel> data);

        void showOptionSelector();

        void hideOptionSelector();

        void navigateToBrandDetail(int brand_id);
    }
}
