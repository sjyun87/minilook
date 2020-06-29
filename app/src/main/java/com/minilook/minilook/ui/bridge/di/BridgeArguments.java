package com.minilook.minilook.ui.bridge.di;

import com.minilook.minilook.ui.bridge.BridgePresenter;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class BridgeArguments {
    private final BridgePresenter.View view;
}