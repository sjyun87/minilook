package com.minilook.minilook.ui.main.fragment.category.di;

import com.minilook.minilook.data.model.category.CategoryItemDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.main.fragment.category.CategoryPresenter;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class CategoryArguments {
    private final CategoryPresenter.View view;
    private final BaseAdapterDataModel<CategoryItemDataModel> adapter;
}