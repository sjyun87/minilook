package com.minilook.minilook.ui.order_exchange_n_return;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface OrderExchangeNReturnPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onTypeBoxClick();

    void onReasonBoxClick();

    void onApplyClick();

    void onReasonDetailTextChanged(String text);

    interface View {

        void setupTypeRecyclerView();

        void typeRefresh();

        void setupReasonRecyclerView();

        void reasonRefresh();

        void setupReasonDetailEditText();

        void setThumbImage(String url);

        void setProductName(String name);

        void setOption(String color, String size);

        void setProductPrice(int price);

        void showTypeBox();

        void hideTypeBox();

        void setSelectedType(String type);

        void setTypeHint();

        void showReasonBox();

        void hideReasonBox();

        void selectedReason(String reason);

        void setReasonHint();

        void enableApplyButton();

        void showReceiptCompleteToast();

        void finish();
    }
}
