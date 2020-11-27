package com.minilook.minilook.ui.lookbook.view.preview;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface LookBookPreviewPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreateView();

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroyView();

    void onPageSelected(int position);

    void onLoadMore();

    interface View {

        void setupViewPager();

        void refresh();

        void refresh(int start, int rows);

        void scrollToNextModule();

        void showErrorDialog();

        void clear();
    }
}
