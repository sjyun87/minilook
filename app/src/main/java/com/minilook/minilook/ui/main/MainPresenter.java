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

    void onMarketingAgree();

    void onMarketingDisagree();

    void onCoachMarkEnd();

    interface View {

        void setupViewPager();

        void setupCurrentPage(int position);

        void setupBottomBar();

        void setBottomBarTheme(boolean flag);

        void showMarketingDialog();

        void updateMarketingAgreeToast(boolean enable);

        void showLookBookCoachMark();

        void showLoadingView();

        void hideLoadingView();

        void navigateToPromotionDetail(int promotionNo);

        void navigateToEventDetail(int eventNo);

        void navigateToProductDetail(int productNo);

        void navigateToBrandDetail(int brandNo);

        void navigateToPreorderDetail(int preorderNo);
    }
}
