package com.minilook.minilook.ui.main.fragment.category.di;

import com.minilook.minilook.ui.main.fragment.category.PreorderPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class PreorderArguments {
    private final PreorderPresenter.View view;
}