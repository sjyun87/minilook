package com.minilook.minilook.ui.search_keyword;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface SearchKeywordPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onSearchEnterClick(String keyword);

    void onRecentClearClick();

    void onRecommendKeywordClick(String keyword);

    interface View {

        void setupClickAction();

        void setupRecentKeywordRecyclerView();

        void recentKeywordRefresh();

        void setupEditText();

        void setSearchKeyword(String keyword);

        void showRecentPanel();

        void hideRecentPanel();

        void addRecommendKeyword(String keyword);

        void showRecommendPanel();

        void hideRecommendPanel();

        void navigateToBridge(String keyword);
    }
}
