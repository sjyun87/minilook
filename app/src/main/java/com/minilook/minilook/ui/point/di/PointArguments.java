package com.minilook.minilook.ui.point.di;

import com.minilook.minilook.ui.point.PointPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class PointArguments {
    private final PointPresenter.View view;
}