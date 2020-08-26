package com.minilook.minilook.ui.brand_detail;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import com.minilook.minilook.data.model.common.SortDataModel;
import java.util.List;

public interface BrandDetailPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onSortClick();

    void onSortSelected(SortDataModel data);

    void onLoadMore();

    void onBrandInfoClick();

    interface View {

        void setupScrollView();

        void setupStyleRecyclerView();

        void styleRefresh();

        void setupSortRecyclerView();

        void sortRefresh();

        void setupSortText(String text);

        void setupProductRecyclerView();

        void productRefresh();

        void productRefresh(int start, int row);

        void showSortPanel();

        void hideSortPanel();

        void setupThumb(String url);

        void setupLogo(String url);

        void setupScrapCount(String text);

        void setupName(String text);

        void setupTag(String text);

        void setupDesc(String text);

        void navigateToBrandInfo(int brand_id);
    }
}
