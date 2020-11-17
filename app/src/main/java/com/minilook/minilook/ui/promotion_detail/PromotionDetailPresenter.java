package com.minilook.minilook.ui.promotion_detail;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface PromotionDetailPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onLoadMore();

    void onShareClick();

    interface View {

        void setupThumb(String url);

        void setupEventImage(String url);

        void setupTotal(int count);

        void setupProductRecyclerView();

        void productRefresh();

        void setupPromotionRecyclerView();

        void promotionRefresh(int start, int rows);

        void sendLink(String shareLink);

        void showErrorMessage();
    }
}
