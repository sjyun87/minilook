package com.minilook.minilook.ui.lookbook.view.detail;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface LookBookDetailPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onBackClick();

    interface View {

        void setupStyleRecyclerView();

        void styleRefresh();

        void setupProductRecyclerView();

        void productRefresh();

        void setupLabel(String text);

        void setupTitle(String text);

        void setupTag(String text);

        void setupDesc(String text);

        void setupSimpleInfo(String text);

        void scrollToTop();
    }
}
