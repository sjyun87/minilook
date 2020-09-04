package com.minilook.minilook.ui.market;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface MarketPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onProductScrap(boolean isScrap, int product_id);

    interface View {

        void setupRecyclerView();

        void refresh();
    }
}
