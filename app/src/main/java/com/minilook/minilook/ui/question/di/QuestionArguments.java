package com.minilook.minilook.ui.question.di;

import com.minilook.minilook.data.model.question.QuestionDataModel;
import com.minilook.minilook.data.model.review.ReviewDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.question.QuestionPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class QuestionArguments {
    private final QuestionPresenter.View view;
    private final int productNo;
    private final BaseAdapterDataModel<QuestionDataModel> adapter;
}