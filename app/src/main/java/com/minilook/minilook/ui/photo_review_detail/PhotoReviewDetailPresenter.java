package com.minilook.minilook.ui.photo_review_detail;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface PhotoReviewDetailPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy();

    void onPageSelected(int position);

    void onMoreClick();

    void onLoadMore();

    interface View {

        void setupClickAction();

        void setupViewPager();

        void refresh();

        void refresh(int start, int row);

        void setCurrentItem(int position);

        void setSelectedPage(int index, int totalPage);

        void setContents(String contents);

        void setExpandButton();

        void collapseButton();

        void clear();
    }
}
