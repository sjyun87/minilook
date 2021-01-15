package com.minilook.minilook.ui.challenge.view.coming;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface ChallengeComingPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreateView();

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroyView();

    void onLogin();

    void onLogout();

    void onLoadMore();

    void onSwipeRefresh();

    interface View {

        void setupSwipeRefresh();

        void setRefreshing(boolean flag);

        void setupRecyclerView();

        void refresh();

        void refresh(int start, int rows);

        void showEmptyPanel();

        void hideEmptyPanel();

        void scrollToTop();

        void clear();
    }
}
