package com.minilook.minilook.ui.category.di;

import com.minilook.minilook.data.model.common.CategoryDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.category.CategoryPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class CategoryArguments {
    private final CategoryPresenter.View view;
    private final BaseAdapterDataModel<CategoryDataModel> adapter;
}