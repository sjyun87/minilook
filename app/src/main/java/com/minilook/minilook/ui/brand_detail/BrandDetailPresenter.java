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

    void onLoadMore();

    void onBrandInfoClick();

    void onShareClick();

    interface View {

        void setupClickAction();

        void setupStyleRecyclerView();

        void styleRefresh();

        void setupSortText(String name);

        void showSortPanel();

        void hideSortPanel();

        void setupProductRecyclerView();

        void productRefresh();

        void productRefresh(int start, int row);

        void setupThumb(String url);

        void setupLogo(String url);

        void setupScrapCount(int count);

        void setupName(String name);

        void setupTag(String tag);

        void setupDesc(String desc);

        void scrapOn();

        void scrapOff();

        void scrollToTop();

        void showErrorDialog();

        void navigateToBrandInfo(int brandNo);

        void navigateToLogin();
    }
}
