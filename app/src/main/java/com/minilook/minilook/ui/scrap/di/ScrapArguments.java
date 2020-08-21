package com.minilook.minilook.ui.scrap.di;

import com.minilook.minilook.ui.scrap.ScrapPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class ScrapArguments {
    private final ScrapPresenter.View view;
}