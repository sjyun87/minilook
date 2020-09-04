package com.minilook.minilook.ui.scrapbook.view.product;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import com.minilook.minilook.data.model.product.ProductDataModel;

public interface ScrapbookProductPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onLoadMore();

    void onEmptyClick();

    void onProductScrap(boolean isScrap, ProductDataModel product);

    interface View {

        void setupRecyclerView();

        void refresh();

        void refresh(int position);

        void refresh(int start, int rows);

        void showEmptyPanel();
    }
}
