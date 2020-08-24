package com.minilook.minilook.ui.guide.di;

import com.minilook.minilook.ui.guide.GuidePresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class GuideArguments {
    private final GuidePresenter.View view;
}