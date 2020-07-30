package com.minilook.minilook.ui.preorder.view.open.di;

import com.minilook.minilook.ui.preorder.adapter.PreorderAdapter;
import com.minilook.minilook.ui.preorder.view.open.PreorderOpenPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class PreorderOpenArguments {
    private final PreorderOpenPresenter.View view;
    private final PreorderAdapter adapter;
}