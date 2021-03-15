package com.minilook.minilook.ui.search_keyword.di;

import com.minilook.minilook.data.model.search.KeywordDataModel;
import com.minilook.minilook.data.room.keyword.KeywordDB;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.search_keyword.SearchKeywordPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class SearchKeywordArguments {
    private final SearchKeywordPresenter.View view;
    private final BaseAdapterDataModel<String> recentKeywordAdapter;
    private final KeywordDB keywordDB;
}