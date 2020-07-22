package com.minilook.minilook.ui.main.fragment.lookbook.view.preview.di;

<<<<<<< HEAD
import com.minilook.minilook.data.model.lookbook.LookBookDataModel;
=======
>>>>>>> 6b560c8b0ec77cb220dd1e0a09f5458a00916e11
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.main.fragment.lookbook.view.preview.LookBookPreviewPresenter;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class LookBookPreviewArguments {
    private final LookBookPreviewPresenter.View view;
    private final BaseAdapterDataModel<LookBookDataModel> adapter;
}