package com.minilook.minilook.ui.search_keyword.di;

import com.minilook.minilook.ui.search_keyword.SearchKeywordPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class SearchKeywordArguments {
    private final SearchKeywordPresenter.View view;
}