package com.minilook.minilook.ui.event_detail;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface EventDetailPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void onResume();

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy();

    void onLoadMore();

    void onShareClick();

    interface View {

        void setupClickAction();

        void setupRecyclerView();

        void refresh();

        void refresh(int start, int rows);

        void setEventImage(String url);

        void hideOtherEvents();

        void showErrorDialog();

        void sendDynamicLink(String link);

        void clear();
    }
}
