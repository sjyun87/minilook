package com.minilook.minilook.ui.review;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface ReviewPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onLoadMore();

    interface View {

        void setTotalCount(int count);

        void setupRecyclerView();

        void refresh();

        void refresh(int start, int rows);

        void emptyPanel();
    }
}
