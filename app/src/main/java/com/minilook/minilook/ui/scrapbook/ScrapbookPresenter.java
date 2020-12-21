package com.minilook.minilook.ui.scrapbook;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface ScrapbookPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy();

    void onTabClick(int position);

    interface View {

        void setupTabLayout();

        void setupViewPager();

        void setupCurrentPage(int position);

        void clear();

        void finish();
    }
}
