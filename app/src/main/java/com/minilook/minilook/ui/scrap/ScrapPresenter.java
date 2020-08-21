package com.minilook.minilook.ui.scrap;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface ScrapPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onTabClick(int position);

    interface View {

        void setupTabLayout();

        void setupCurrentPage(int position);

        void scrollToTop();

        void setupViewPager();
    }
}
