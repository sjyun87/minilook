package com.minilook.minilook.ui.lookbook.view.detail;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface LookBookDetailPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onBackClick();

    void onProductScrap(boolean isScrap, int product_id);

    interface View {

        void setupStyleRecyclerView();

        void setupProductRecyclerView();

        void setupLabel(String text);

        void setupTitle(String text);

        void setupTag(String text);

        void setupDesc(String text);

        void setupProductInfo(String text);

        void styleRefresh();

        void productRefresh();

        void productRefresh(int position);

        void scrollToTop();
    }
}
