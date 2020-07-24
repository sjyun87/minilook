package com.minilook.minilook.ui.main;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface MainPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onTabChanged(int position);

    interface View {

        void setupViewPager();

        void setupBottomBar();

        void navigateToBrandDetail(int id);

        void setupBottomBarTheme(boolean flag);
    }
}
