package com.minilook.minilook.ui.review_write;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface ReviewWritePresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onTextChanged(String text);

    void onApplyClick();

    interface View {

        void setThumb(String url);

        void setBrandName(String name);

        void setProductName(String name);

        void setOption(String color, String size);

        void setupReviewEditText();

        void enableApplyButton();

        void disableApplyButton();

        void showReviewWriteToast();

        void finish();
    }
}
