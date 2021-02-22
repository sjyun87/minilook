package com.minilook.minilook.ui.question_edit.di;

import com.minilook.minilook.data.model.gallery.PhotoDataModel;
import com.minilook.minilook.data.model.question.QuestionDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.question_edit.QuestionEditPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class QuestionEditArguments {
    private final QuestionEditPresenter.View view;
    private final QuestionDataModel data;
    private final BaseAdapterDataModel<String> typeAdapter;
    private final BaseAdapterDataModel<PhotoDataModel> photoAdapter;
}