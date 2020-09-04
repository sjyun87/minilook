package com.minilook.minilook.ui.scrapbook.view.brand;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import com.minilook.minilook.data.model.brand.BrandDataModel;

public interface ScrapbookBrandPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onLoadMore();

    void onEmptyClick();

    void onBrandScrap(boolean isScrap, BrandDataModel brand);

    interface View {

        void setupRecyclerView();

        void refresh();

        void refresh(int position);

        void refresh(int start, int rows);

        void showEmptyPanel();
    }
}
