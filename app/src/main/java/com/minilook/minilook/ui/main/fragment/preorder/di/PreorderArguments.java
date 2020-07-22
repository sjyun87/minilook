package com.minilook.minilook.ui.main.fragment.preorder.di;

import com.minilook.minilook.ui.main.fragment.preorder.PreorderPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class PreorderArguments {
    private final PreorderPresenter.View view;
}