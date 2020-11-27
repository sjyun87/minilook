package com.minilook.minilook.ui.lookbook;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface LookBookPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreateView();

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void onResume();

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroyView();

    void onPageSelected(int position);

    interface View {

        void setupViewPager();

        void scrollToPreviewPage(boolean smoothScroll);

        void scrollToDetailPage(boolean smoothScroll);

        void clear();
    }
}
