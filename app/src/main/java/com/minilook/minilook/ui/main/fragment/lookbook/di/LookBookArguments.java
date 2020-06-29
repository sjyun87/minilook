package com.minilook.minilook.ui.main.fragment.lookbook.di;

import com.minilook.minilook.ui.main.fragment.lookbook.LookBookPresenter;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class LookBookArguments {
    private final LookBookPresenter.View view;
}