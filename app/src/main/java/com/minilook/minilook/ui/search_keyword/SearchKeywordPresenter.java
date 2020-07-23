package com.minilook.minilook.ui.search_keyword;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface SearchKeywordPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onSearchEnterClick(String keyword);

    void onKeywordClick(String keyword);

    void onDeleteClick(String keyword);

    void removeAllClick();

    interface View {

        void setupEditText();

        void showRecentPanel();

        void hideRecentPanel();

        void setupPopularTitle(String text);

        void setupBrandTitle(String text);

        void navigateToBridge(String keyword);

        void addKeywordView(String keyword);

        void removeAllKeywordView();

        void removeOldKeywordView();
    }
}
