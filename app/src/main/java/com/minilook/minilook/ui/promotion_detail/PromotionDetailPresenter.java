package com.minilook.minilook.ui.promotion_detail;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface PromotionDetailPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void onResume();

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy();

    void onLoadMore();

    void onShareClick();

    interface View {

        void setupClickAction();

        void setupProductRecyclerView();

        void productRefresh();

        void setupPromotionRecyclerView();

        void promotionRefresh();

        void promotionRefresh(int start, int rows);

        void setThumb(String url);

        void setEventImage(String url);

        void setTotal(int count);

        void hideOtherPromotions();

        void showErrorDialog();

        void clear();
    }
}
