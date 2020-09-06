package com.minilook.minilook.ui.product_bridge;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface ProductBridgePresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onLoadMore();

    interface View {

        void setupProductRecyclerView();

        void productRefresh();

        void productRefresh(int start, int rows);
    }
}
