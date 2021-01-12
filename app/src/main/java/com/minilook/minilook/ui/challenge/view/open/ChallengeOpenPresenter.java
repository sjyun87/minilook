package com.minilook.minilook.ui.challenge.view.open;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface ChallengeOpenPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreateView();

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroyView();

    void onLoadMore();

    interface View {

        void setupRecyclerView();

        void refresh();

        void refresh(int start, int rows);

        void showEmptyPanel();

        void hideEmptyPanel();

        void clear();
    }
}
