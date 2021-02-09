package com.minilook.minilook.ui.review_history;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface ReviewHistoryPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onWrittenReviewClick();

    void onWritableReviewClick();

    void onPageSelected(int position);

    interface View {

        void setupClickAction();

        void setupViewPager();

        void setCurrentPage(int page);

        void enableWrittenReviewButton();

        void disableWrittenReviewButton();

        void enableWritableReviewButton();

        void disableWritableReviewButton();
    }
}
