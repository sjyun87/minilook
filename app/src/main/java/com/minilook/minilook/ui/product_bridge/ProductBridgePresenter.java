package com.minilook.minilook.ui.product_bridge;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import com.minilook.minilook.data.model.common.CategoryDataModel;
import java.util.List;

public interface ProductBridgePresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onLoadMore();

    void onTabClick(int position);

    void onMenuClick(int position);

    interface View {

        void setupTitle(String title);

        void setupTabItems(List<CategoryDataModel> categories);

        void setupOptionRecyclerView();

        void optionMenuRefresh();

        void setupProductRecyclerView();

        void productRefresh();

        void productRefresh(int start, int rows);

        void showEmptyPanel();

        void hideEmptyPanel();
    }
}
