package com.minilook.minilook.ui.question;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.base.widget.TitleBar;
import com.minilook.minilook.ui.question.di.QuestionArguments;

public class QuestionActivity extends BaseActivity implements QuestionPresenter.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, QuestionActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    @BindView(R.id.titlebar) TitleBar titleBar;
    @BindView(R.id.rcv_question) RecyclerView recyclerView;

    private QuestionPresenter presenter;

    @Override protected int getLayoutID() {
        return R.layout.activity_question;
    }

    @Override protected void createPresenter() {
        presenter = new QuestionPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private QuestionArguments provideArguments() {
        return QuestionArguments.builder()
            .view(this)
            .build();
    }

    @Override public void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override public void setupCount(int count) {
        titleBar.setCount(count);
    }
}
