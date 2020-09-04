package com.minilook.minilook.ui.lookbook.view.preview;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface LookBookPreviewPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onPageSelected(int position);

    void onProductScrap(boolean isScrap, int product_id);

    interface View {

        void setupViewPager();

        void refresh();

        void refresh(int position);

        void refresh(int start, int rows);
    }
}
