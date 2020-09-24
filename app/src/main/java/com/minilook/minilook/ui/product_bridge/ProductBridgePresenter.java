package com.minilook.minilook.ui.product_bridge;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import com.minilook.minilook.data.model.common.CategoryDataModel;
import com.minilook.minilook.data.model.common.CodeDataModel;
import java.util.List;

public interface ProductBridgePresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onLoadMore();

    void onTabClick(int position);

    void onSortClick();

    void onSortSelected(CodeDataModel data);

    void onFilterClick();

    interface View {

        void setupTitle(String title);

        void setupTabItems(List<CategoryDataModel> categories);

        void setupSortRecyclerView();

        void sortRefresh();

        void setupSortText(String name);

        void showSortPanel();

        void hideSortPanel();

        void setupProductRecyclerView();

        void productRefresh();

        void productRefresh(int start, int rows);

        void showEmptyPanel();

        void hideEmptyPanel();

        void navigateToSearchFilter();

        void finish();
    }
}
