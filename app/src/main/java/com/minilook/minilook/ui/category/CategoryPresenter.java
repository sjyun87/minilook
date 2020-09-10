package com.minilook.minilook.ui.category;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface CategoryPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onBannerVideoClick();

    interface View {

        void setupRecyclerView();

        void refresh();

      void navigateToYoutube(String url);
    }
}
