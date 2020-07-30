package com.minilook.minilook.ui.preorder.view.coming.di;

import com.minilook.minilook.ui.preorder.adapter.PreorderAdapter;
import com.minilook.minilook.ui.preorder.view.coming.PreorderComingPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class PreorderComingArguments {
    private final PreorderComingPresenter.View view;
    private final PreorderAdapter adapter;
}