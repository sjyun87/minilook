package com.minilook.minilook.ui.scrapbook.di;

import com.minilook.minilook.ui.scrapbook.ScrapbookPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class ScrapbookArguments {
    private final ScrapbookPresenter.View view;
}