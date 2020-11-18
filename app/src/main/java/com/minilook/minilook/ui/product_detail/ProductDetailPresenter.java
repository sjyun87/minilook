package com.minilook.minilook.ui.product_detail;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import com.minilook.minilook.data.model.product.OptionColorDataModel;
import com.minilook.minilook.data.model.product.ProductStockDataModel;
import com.minilook.minilook.data.model.shopping.ShoppingBrandDataModel;
import com.minilook.minilook.data.model.shopping.ShoppingOptionDataModel;
import java.util.List;

public interface ProductDetailPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void onResume();

    void onTabClick(int position);

    void onReviewMoreClick();

    void onQuestionClick();

    void onScrapClick();

    void onBuyClick();

    void onBrandClick();

    void onExpandClick();

    void onShippingNRefundClick();

    void onOptionSelectorShoppingBagClick(List<ShoppingOptionDataModel> optionData);

    void onOptionSelectorBuyClick(List<ShoppingOptionDataModel> optionData);

    void onTrialVersionDialogGoClick();

    void onShareClick();

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

        void addColorView(ProductStockDataModel model);

        void addSizeView(ProductStockDataModel model);

        void setupPriceOrigin(String text);

        void showPriceOrigin();

        void hidePriceOrigin();

        void setupDiscountPercent(int percent);

        void showDiscountPercent();

        void hideDiscountPercent();

        void setupPrice(String text);

        void setupPoint(int point);

        void setupShippingFree();

        void setupShippingPrice(int price);

        void setupShippingCondition(int price);

        void showShippingCondition();

        void hideShippingCondition();

        void scrollToProductInfo();

        void scrollToReview();

        void scrollToQuestion();

        void scrollToShippingNRefund();

        void setupProductDetail(String htmlText);

        void setupReviewCount(String text);

        void showReviewContentsPanel();

        void setupQuestionCount(String text);

        void setupOptionSelector(int price, List<OptionColorDataModel> options);

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

        void checkScrap();

        void uncheckScrap();

        void hideScrap();

        void navigateToProductInfo(int brand_id);

        void navigateToBrandDetail(int brand_id);

        void navigateToShoppingBag();

        void showTrialVersionDialog();

        void showAddShoppingBagToast();

        void navigateToEventDetail();

        void navigateToOrder(List<ShoppingBrandDataModel> items);

        void navigateToLogin();

        void navigateToReview(int productNo);

        void navigateToQuestion(int productNo);

        void sendLink(String shareLink);

        void showErrorMessage();
    }
}
