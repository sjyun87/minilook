package com.minilook.minilook.ui.question_write.di;

import com.minilook.minilook.ui.question_write.QuestionWritePresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class QuestionWriteArguments {
    private final QuestionWritePresenter.View view;
    private final int productNo;
}