package com.minilook.minilook.ui.event_detail;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface EventDetailPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void onResume();

    void onLoadMore();

    void onShareClick();

    interface View {

        void setupEventImage(String url);

        void setupRecyclerView();

        void refresh(int start, int rows);

        void sendLink(String shareLink);

        void showErrorMessage();
    }
}
