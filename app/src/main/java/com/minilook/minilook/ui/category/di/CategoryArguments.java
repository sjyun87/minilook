package com.minilook.minilook.ui.category.di;

import com.minilook.minilook.ui.category.CategoryPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class CategoryArguments {
    private final CategoryPresenter.View view;
}