package com.minilook.minilook.ui.point;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface PointPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onPointInfoClick();

    interface View {

        void setupPoint(int point);

        void setupRecyclerView();

        void refresh();

        void emptyPanel();

        void navigateToWebView(String url);
    }
}
