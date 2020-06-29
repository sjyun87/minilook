package com.minilook.minilook.ui.main.fragment.lookbook.view.detail;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface LookBookDetailPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    interface View {

        void setupViewPager();

        void setupRecyclerView();

        void setTitle(String text);

        void setDescription(String text);

        void imageRefresh();

        void productRefresh();
    }
}
