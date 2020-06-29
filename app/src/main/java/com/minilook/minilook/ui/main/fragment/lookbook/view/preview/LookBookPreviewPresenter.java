package com.minilook.minilook.ui.main.fragment.lookbook.view.preview;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface LookBookPreviewPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onPageSelected(int position);

    interface View {

        void setupViewPager();

        void refresh();
    }
}
