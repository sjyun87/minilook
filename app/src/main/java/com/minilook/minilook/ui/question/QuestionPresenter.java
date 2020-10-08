package com.minilook.minilook.ui.question;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface QuestionPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onLoadMore();

    void onEmptyClick();

    interface View {

        void setupTitleBar(int productNo);

        void setTotalCount(int count);

        void setupRecyclerView();

        void refresh();

        void refresh(int start, int rows);

        void showEmptyPanel();

        void hideEmptyPanel();

        void navigateToQuestionWrite(int productNo);

        void navigateToLogin();
    }
}
