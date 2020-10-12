package com.minilook.minilook.ui.preorder.view.close.di;

import com.minilook.minilook.data.model.preorder.PreorderDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.preorder.view.close.PreorderClosePresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class PreorderCloseArguments {
    private final PreorderClosePresenter.View view;
    private final BaseAdapterDataModel<PreorderDataModel> adapter;
}