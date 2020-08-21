package com.minilook.minilook.ui.recent.di;

import com.minilook.minilook.ui.recent.RecentPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class RecentArguments {
    private final RecentPresenter.View view;
}