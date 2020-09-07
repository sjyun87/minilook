package com.minilook.minilook.ui.event_detail;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface EventDetailPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onLoadMore();

    interface View {

        void setupEventImage(String url);

        void setupRecyclerView();

        void refresh(int start, int rows);
    }
}
