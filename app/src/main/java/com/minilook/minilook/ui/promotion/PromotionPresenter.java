package com.minilook.minilook.ui.promotion;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface PromotionPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    interface View {

        void setupRecyclerView();

        void setupBgImage(String url);

        void setupDesc(String text);

        void setupTitle(String text);

        void refresh();
    }
}
