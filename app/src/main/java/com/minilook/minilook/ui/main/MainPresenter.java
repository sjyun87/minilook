package com.minilook.minilook.ui.main;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface MainPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy();

    void onMarketingAgree();

    void onMarketingDisagree();

    void onCoachMarkEnd();

    void onBottomBarClick(int position);

    interface View {

        void setupViewPager();

        void setCurrentPage(int position);

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

        void clear();
    }
}
