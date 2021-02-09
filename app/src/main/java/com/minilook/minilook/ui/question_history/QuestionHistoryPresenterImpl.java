package com.minilook.minilook.ui.question_history;

import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.question_history.di.QuestionHistoryArguments;

public class QuestionHistoryPresenterImpl extends BasePresenterImpl implements QuestionHistoryPresenter {

    private final View view;

    public QuestionHistoryPresenterImpl(QuestionHistoryArguments args) {
        view = args.getView();
    }

    @Override public void onCreate() {

    }
}