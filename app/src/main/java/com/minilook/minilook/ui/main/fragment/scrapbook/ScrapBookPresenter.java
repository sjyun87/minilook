package com.minilook.minilook.ui.main.fragment.scrapbook;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface ScrapBookPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onProductClick();

    void onBrandClick();

    void onRecentClick();

    interface View {

        void setCurrentPage(int page);

        void setProductButtonUI();

        void setBrandButtonUI();

        void setRecentButtonUI();
    }
}
