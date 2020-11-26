package com.minilook.minilook.ui.preorder.di;

import com.minilook.minilook.ui.preorder.PreorderPresenter;
import com.minilook.minilook.data.firebase.DynamicLinkManager;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class PreorderArguments {
    private final PreorderPresenter.View view;
    private final DynamicLinkManager dynamicLinkManager;
}