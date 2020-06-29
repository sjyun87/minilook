package com.minilook.minilook.ui.main.fragment.lookbook.view.preview.di;

import com.minilook.minilook.data.model.lookbook.LookBookModuleDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.main.fragment.lookbook.view.preview.LookBookPreviewPresenter;
import com.minilook.minilook.ui.main.fragment.lookbook.view.preview.adapter.LookBookModuleAdapter;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class LookBookPreviewArguments {
    private final LookBookPreviewPresenter.View view;
    private final BaseAdapterDataModel<LookBookModuleDataModel> adapter;
}