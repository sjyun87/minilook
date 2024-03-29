package com.minilook.minilook.ui.order_history;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface OrderHistoryPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    interface View {

        void setupRecyclerView();

        void refresh();

        void refresh(int start, int rows);

        void showEmptyPanel();

        void navigateToOrderDetail(String order_id, String receipt_id);
    }
}
