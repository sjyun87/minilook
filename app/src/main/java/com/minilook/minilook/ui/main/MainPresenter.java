package com.minilook.minilook.ui.main;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface MainPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void onResume();

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void onPause();

    void onTabChanged(int position);

    void onMarketingAgree();

    interface View {

        void setupViewPager();

        void setupCurrentPage(int position);

        void setupBottomBar();

        void setupBottomBarTheme(boolean flag);

        void showMarketingDialog();

        void navigateToLogin();
    }
}
