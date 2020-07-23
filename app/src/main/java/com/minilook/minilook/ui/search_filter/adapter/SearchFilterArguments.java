package com.minilook.minilook.ui.search_filter.adapter;

import com.minilook.minilook.ui.search_filter.SearchFilterPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class SearchFilterArguments {
    private final SearchFilterPresenter.View view;
}