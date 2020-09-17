package com.minilook.minilook.ui.order_cancel;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface OrderCancelPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onApplyClick();

    interface View {

        void setupRecyclerView();

        void refresh();

        void setOrderNo(String no);

        void setOrderDate(String date);

        void showReceiptCompletedToast();

        void finish();

        void showErrorToast(String message);
    }
}
