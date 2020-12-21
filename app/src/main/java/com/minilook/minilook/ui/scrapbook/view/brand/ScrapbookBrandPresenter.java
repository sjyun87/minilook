package com.minilook.minilook.ui.scrapbook.view.brand;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import com.minilook.minilook.data.model.brand.BrandDataModel;

public interface ScrapbookBrandPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreateView();

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroyView();

    void onLoadMore();

    void onEmptyClick();

    void onBrandScrap(BrandDataModel data);

    interface View {

        void setupClickAction();

        void setupRecyclerView();

        void refresh();

        void refresh(int position);

        void refresh(int start, int rows);

        void showEmptyPanel();

        void showErrorDialog();

        void clear();
    }
}
