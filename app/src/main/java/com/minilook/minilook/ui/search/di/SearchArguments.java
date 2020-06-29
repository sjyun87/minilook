package com.minilook.minilook.ui.search.di;

import com.minilook.minilook.data.room.recent_keyword.RecentKeywordDB;
import com.minilook.minilook.ui.search.SearchPresenter;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class SearchArguments {
    private final SearchPresenter.View view;
    private final RecentKeywordDB recentKeywordDB;
}