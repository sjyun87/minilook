package com.minilook.minilook.ui.main.fragment.lookbook.view.detail;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface LookBookDetailPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onBackClick();

    interface View {

        void setupStyleRecyclerView();

        void setupProductRecyclerView();

        void setupLabel(String text);

        void setupTitle(String text);

        void setupTag(String text);

        void setupDescription(String text);

        void setupProductInfo(String text);

        void styleRefresh();

        void productRefresh();
    }
}
