package com.minilook.minilook.ui.preorder_detail;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface PreorderDetailPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onTabClick(int position);

    interface View {

        void setupViewPager();

        void imageRefresh();

        void setupTabLayout();

        void setupWebView();

        void setPreorderWebView(String url);

        void setupRecyclerView();

        void productRefresh();

        void setBrandName(String name);

        void setTitle(String title);

        void setRemainDate(int count);

        void setTermDate(String date);

        void setDeliveryDate(String date);

        void setEnableLabelBackground();

        void setDisableLabelBackground();

        void setLabel(String label);

        void enableBuyButton();

        void disableBuyButton(String name);

        void showCloseTextView();

        void scrollToPreorderInfo();

        void scrollToProduct();

        void scrollToShippingNRefund();

        void navigateToPreorderProductDetail(String title, int preorderNo, int productNo);
    }
}
