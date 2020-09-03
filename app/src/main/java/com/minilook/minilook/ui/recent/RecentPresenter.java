package com.minilook.minilook.ui.recent;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface RecentPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onLoadMore();

    void onEmptyClick();

    interface View {

        void setupRecyclerView();

        void refresh();

        void refresh(int start, int rows);

        void showEmptyPanel();

        void finish();
    }
}
