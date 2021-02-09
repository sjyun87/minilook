package com.minilook.minilook.ui.question_history;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import com.minilook.minilook.databinding.ActivityQuestionHistoryBinding;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.question_history.di.QuestionHistoryArguments;

public class QuestionHistoryActivity extends BaseActivity implements QuestionHistoryPresenter.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, QuestionHistoryActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    private ActivityQuestionHistoryBinding binding;
    private QuestionHistoryPresenter presenter;

    @Override protected View getBindingView() {
        binding = ActivityQuestionHistoryBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override protected void createPresenter() {
        presenter = new QuestionHistoryPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private QuestionHistoryArguments provideArguments() {
        return QuestionHistoryArguments.builder()
            .view(this)
            .build();
    }
}
