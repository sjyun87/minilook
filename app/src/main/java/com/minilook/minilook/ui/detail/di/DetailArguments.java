package com.minilook.minilook.ui.detail.di;

import com.minilook.minilook.ui.detail.DetailPresenter;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class DetailArguments {
    private final DetailPresenter.View view;
    private final String webUrl;
}