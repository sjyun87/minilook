package com.minilook.minilook.ui.question;

import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.question.di.QuestionArguments;

public class QuestionPresenterImpl extends BasePresenterImpl implements QuestionPresenter {

    private final View view;

    public QuestionPresenterImpl(QuestionArguments args) {
        view = args.getView();
    }

    @Override public void onCreate() {
        view.setupRecyclerView();

        view.setupCount(10);
    }
}