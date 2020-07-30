package com.minilook.minilook.ui.brand_detail;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import com.minilook.minilook.data.model.common.SortDataModel;
import java.util.List;

public interface BrandDetailPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onCategoryItemClick(int position);

    void onSortClick();

    void onSortSelected(SortDataModel data);

    interface View {

        void setupStyleRecyclerView();

        void styleRefresh();

        void setupPickRecyclerView();

        void pickRefresh();

        void setupCategoryRecyclerView();

        void categoryRefresh();

        void setupSortRecyclerView();

        void sortRefresh();

        void setupSortText(String text);

        void showSortPanel();

        void hideSortPanel();

        void setupThumb(String url);

        void setupLogo(String url);

        void setupScrapCount(String text);

        void setupName(String text);

        void setupTag(String text);

        void setupDesc(String text);
    }
}
