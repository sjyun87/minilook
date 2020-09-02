package com.minilook.minilook.ui.scrapbook;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface ScrapbookPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onTabClick(int position);

    interface View {

        void setupTabLayout();

        void setupViewPager();

        void setupCurrentPage(int position);
    }
}
