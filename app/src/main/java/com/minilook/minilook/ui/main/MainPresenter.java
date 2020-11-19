package com.minilook.minilook.ui.main;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import com.minilook.minilook.data.model.brand.BrandDataModel;
import com.minilook.minilook.data.model.product.ProductDataModel;

public interface MainPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onTabChanged(int position);

    void onProductScrap(boolean isScrap, ProductDataModel product);

    void onBrandScrap(boolean isScrap, BrandDataModel brand);

    void onMarketingAgree();

    void onMarketingDismiss();

    void onCoachMarkEnd();

    interface View {

        void setupViewPager();

        void setupCurrentPage(int position);

        void setupBottomBar();

        void setupBottomBarTheme(boolean flag);

        void showMarketingDialog();

        void showLookBookCoachMark();

        void navigateToPromotionDetail(int promotionNo);

        void navigateToEventDetail(int eventNo);

        void navigateToProductDetail(int productNo);

        void navigateToBrandDetail(int brandNo);

        void navigateToPreorderDetail(int preorderNo);
    }
}
