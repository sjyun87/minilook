package com.minilook.minilook.ui.guide;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface GuidePresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy();

    void onPageSelected(int position);

    void onGuideEnd();

    interface View {

        void setupViewPager();

        void setupGuideListener();

        void showStartButton();

        void hideStartButton();

        void navigateToMain();

        void clear();

        void finish();
    }
}
