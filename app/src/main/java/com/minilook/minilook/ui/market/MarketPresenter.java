package com.minilook.minilook.ui.market;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface MarketPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreateView();

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void onResume();

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void onPause();

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroyView();

    void onRefresh();

    interface View {

        void setupRefreshLayout();

        void setRefreshing(boolean flag);

        void setupRecyclerView();

        void refresh();

        void attachedToWindow();

        void detachToWindow();

        void showErrorDialog();

        void clear();
    }
}
