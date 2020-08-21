package com.minilook.minilook.ui.question.di;

import com.minilook.minilook.ui.question.QuestionPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class QuestionArguments {
    private final QuestionPresenter.View view;
}