package com.minilook.minilook.ui.lookbook;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface LookBookPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onPrePageSelected(int position);

    void onPageSelected(int position);

    interface View {

        void setupViewPager();

        void navigateToPreviewPage(boolean smoothScroll);

        void navigateToDetailPage(boolean smoothScroll);
    }
}
