package com.minilook.minilook.ui.preorder.view.open.di;

import com.minilook.minilook.data.model.preorder.PreorderDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.preorder.view.open.PreorderOpenPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class PreorderOpenArguments {
    private final PreorderOpenPresenter.View view;
    private final BaseAdapterDataModel<PreorderDataModel> adapter;
}