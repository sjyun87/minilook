package com.minilook.minilook.ui.brand;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface BrandPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void resetClick();

    interface View {

        void setupStyleRecyclerView();

        void setupBrandRecyclerView();

        void brandRefresh();
    }
}
