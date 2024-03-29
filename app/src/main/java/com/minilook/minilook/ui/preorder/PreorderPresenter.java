package com.minilook.minilook.ui.preorder;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface PreorderPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void onResume();

    void onTabClick(int position);

    interface View {

        void setupTabLayout();

        void setupViewPager();

        void setupCurrentPage(int position);

        void navigateToPreorderInfo();

        void hideClosePreorderTab();

        void showErrorDialog();

        void navigateToPreorderDetail(int preorderNo);

        void sendDynamicLink(String link);
    }
}
