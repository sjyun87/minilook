package com.minilook.minilook.ui.preorder.view.coming.di;

import com.minilook.minilook.data.model.preorder.PreorderDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.preorder.view.coming.PreorderComingPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class PreorderComingArguments {
    private final PreorderComingPresenter.View view;
    private final BaseAdapterDataModel<PreorderDataModel> adapter;
}