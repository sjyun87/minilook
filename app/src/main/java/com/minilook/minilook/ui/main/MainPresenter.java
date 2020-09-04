package com.minilook.minilook.ui.main;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface MainPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onTabChanged(int position);

    void onMarketingAgree();

    void onProductScrap(boolean isScrap, int product_id);

    interface View {

        void setupViewPager();

        void setupCurrentPage(int position);

        void setupBottomBar();

        void setupBottomBarTheme(boolean flag);

        void showMarketingDialog();

        void navigateToLogin();
    }
}
