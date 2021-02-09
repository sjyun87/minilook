package com.minilook.minilook.ui.order_complete;

import androidx.lifecycle.LifecycleObserver;

public interface OrderCompletePresenter extends LifecycleObserver {

    void onOrderHistoryClick();

    void onShoppingClick();

    interface View {

        void navigateToOrderHistory();

        void navigateToMain();

        void finish();
    }
}
