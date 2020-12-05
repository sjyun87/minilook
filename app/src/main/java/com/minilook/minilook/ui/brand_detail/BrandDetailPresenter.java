package com.minilook.minilook.ui.brand_detail;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import com.minilook.minilook.data.model.common.CodeDataModel;

public interface BrandDetailPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void onResume();

    void onScrapClick();

    void onSortClick();

    void onSortSelected(CodeDataModel data);

    void onLoadMore();

    void onBrandInfoClick();

    void onShareClick();

    interface View {

        void setupClickAction();

        void setupStyleRecyclerView();

        void styleRefresh();

        void setupSortSelector();

        void showSortSelector();

        void hideSortSelector();

        void setupSortText(String name);

        void setupProductRecyclerView();

        void productRefresh();

        void productRefresh(int start, int row);

        void setThumb(String url);

        void setLogo(String url);

        void setScrapCount(int count);

        void setName(String name);

        void setTag(String tag);

        void setDesc(String desc);

        void scrapOn();

        void scrapOff();

        void scrollToTop();

        void showErrorDialog();

        void navigateToBrandInfo(int brandNo);

        void navigateToLogin();

    }
}
