package com.minilook.minilook.ui.lookbook.view.detail;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface LookBookDetailPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreateView();

    void onBackClick();

    interface View {

        void setupTitleBar();

        void setupStyleRecyclerView();

        void styleRefresh();

        void setupProductRecyclerView();

        void productRefresh();

        void setLabel(String text);

        void setTitle(String text);

        void setTag(String text);

        void setDesc(String text);

        void setSimpleInfo(String text);

        void scrollToTop();
    }
}
