package com.minilook.minilook.ui.market;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import com.minilook.minilook.data.model.search.SearchOptionDataModel;

public interface MarketPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreateView();

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void onResume();

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void onPause();

    void onRefresh();

    interface View {

        void setupRefreshLayout();

        void setRefreshing(boolean flag);

        void setupRecyclerView();

        void refresh();

        void attachedToWindow();

        void detachToWindow();

        void showErrorDialog();
    }
}
