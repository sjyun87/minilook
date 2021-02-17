package com.minilook.minilook.ui.question_history.di;

import com.minilook.minilook.data.model.question.QuestionDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.question_history.QuestionHistoryPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class QuestionHistoryArguments {
    private final QuestionHistoryPresenter.View view;
    private final BaseAdapterDataModel<QuestionDataModel> adapter;
}