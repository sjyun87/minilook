package com.minilook.minilook.ui.question_write.di;

import com.minilook.minilook.data.model.gallery.PhotoDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.question_write.QuestionWritePresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class QuestionWriteArguments {
    private final QuestionWritePresenter.View view;
    private final int productNo;
    private final BaseAdapterDataModel<PhotoDataModel> photoAdapter;
}