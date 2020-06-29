package com.minilook.minilook.ui.main.di;

import com.minilook.minilook.ui.main.MainPresenter;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class MainArguments {
    private final MainPresenter.View view;
}