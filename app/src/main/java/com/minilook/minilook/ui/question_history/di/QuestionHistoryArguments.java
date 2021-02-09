package com.minilook.minilook.ui.question_history.di;

import com.minilook.minilook.ui.question_history.QuestionHistoryPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class QuestionHistoryArguments {
    private final QuestionHistoryPresenter.View view;
}