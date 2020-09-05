package com.minilook.minilook.ui.search_zip.di;

import com.minilook.minilook.ui.search_zip.SearchZipPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class SearchZipArguments {
    private final SearchZipPresenter.View view;
}