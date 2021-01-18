package com.minilook.minilook.ui.challenge.view.close;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface ChallengeClosePresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreateView();

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroyView();

    void onLogin();

    void onLogout();

    interface View {

        void setupRecyclerView();

        void refresh();

        void refresh(int start, int rows);

        void scrollToTop();

        void clear();
    }
}
