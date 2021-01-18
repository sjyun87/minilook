package com.minilook.minilook.ui.challenge;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface ChallengePresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreateView();

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void onResume();

    void onLogin();

    void onLogout();

    void onSwipeRefresh();

    interface View {

        void setupSwipeRefresh();

        void setRefreshing(boolean flag);

        void setupTabLayout();

        void setupViewPager();

        void setHeaderExpand();

        void navigateToChallengeDetail(int challengeNo);
    }
}
