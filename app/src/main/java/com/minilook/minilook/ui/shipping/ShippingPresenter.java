package com.minilook.minilook.ui.shipping;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface ShippingPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onUpdateDefaultOkClick();

    interface View {

        void setupRecyclerView();

        void showEmptyPanel();

        void refresh();

        void showUpdateShippingDialog();

        void finish();
    }
}
