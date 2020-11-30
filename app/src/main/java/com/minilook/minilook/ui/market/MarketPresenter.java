package com.minilook.minilook.ui.market;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import com.minilook.minilook.data.model.search.SearchOptionDataModel;

public interface MarketPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void onResume();

    void onRefresh();

    interface View {

        void setupRefreshLayout();

        void setRefreshing();

        void setupRecyclerView();

        void refresh();

        void showErrorDialog();

        void navigateToProductBridge(SearchOptionDataModel model);

        void navigateToPromotionDetail(int promotionNo);
    }
}
