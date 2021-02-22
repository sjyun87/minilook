package com.minilook.minilook.ui.question;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import androidx.annotation.DimenRes;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.fondesa.recyclerviewdivider.DividerDecoration;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.question.QuestionDataModel;
import com.minilook.minilook.databinding.ActivityQuestionBinding;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.base.listener.EndlessOnScrollListener;
import com.minilook.minilook.ui.dialog.manager.DialogManager;
import com.minilook.minilook.ui.login.LoginActivity;
import com.minilook.minilook.ui.question.adapter.QuestionAdapter;
import com.minilook.minilook.ui.question.di.QuestionArguments;
import com.minilook.minilook.ui.question_write.QuestionWriteActivity;

public class QuestionActivity extends BaseActivity implements QuestionPresenter.View {

    public static void start(Context context, int productNo) {
        Intent intent = new Intent(context, QuestionActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("productNo", productNo);
        context.startActivity(intent);
    }

    @DimenRes int dp_6 = R.dimen.dp_6;

    private ActivityQuestionBinding binding;
    private QuestionPresenter presenter;

    private final QuestionAdapter adapter = new QuestionAdapter();
    private final BaseAdapterDataView<QuestionDataModel> adapterView = adapter;

    @Override protected View getBindingView() {
        binding = ActivityQuestionBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override protected void createPresenter() {
        presenter = new QuestionPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private QuestionArguments provideArguments() {
        return QuestionArguments.builder()
            .view(this)
            .productNo(getIntent().getIntExtra("productNo", -1))
            .adapter(adapter)
            .build();
    }

    @Override public void setupClickAction() {
        binding.titlebar.getBinding().imgTitlebarWrite.setOnClickListener(view -> presenter.onWriteClick());
        binding.txtEmpty.setOnClickListener(view -> presenter.onEmptyClick());
    }

    @Override public void setTotalCount(int count) {
        binding.titlebar.setCount(count);
        binding.titlebar.setShowCount(true);
    }

    @Override public void setupRecyclerView() {
        binding.rcvQuestion.setLayoutManager(new LinearLayoutManager(this));
        binding.rcvQuestion.setAdapter(adapter);
        DividerDecoration.builder(this)
            .size(resources.getDimen(dp_6))
            .asSpace()
            .build()
            .addTo(binding.rcvQuestion);
        EndlessOnScrollListener scrollListener =
            EndlessOnScrollListener.builder()
                .layoutManager(binding.rcvQuestion.getLayoutManager())
                .onLoadMoreListener(presenter::onLoadMore)
                .visibleThreshold(10)
                .build();
        binding.rcvQuestion.addOnScrollListener(scrollListener);
    }

    @Override public void refresh() {
        adapterView.refresh();
    }

    @Override public void refresh(int start, int rows) {
        adapterView.refresh(start, rows);
    }

    @Override public void showEmptyPanel() {
        binding.layoutEmptyPanel.setVisibility(View.VISIBLE);
    }

    @Override public void hideEmptyPanel() {
        binding.layoutEmptyPanel.setVisibility(View.GONE);
    }

    @Override public void navigateToQuestionWrite(int productNo) {
        QuestionWriteActivity.start(this, productNo);
    }

    @Override public void navigateToLogin() {
        LoginActivity.start(this);
    }

    @Override public void showQuestionDeleteDialog(int productNo, int questionNo) {
        DialogManager.showQuestionDeleteDialog(this, () -> presenter.onQuestionDelete(productNo, questionNo));
    }

    @Override public void showErrorDialog() {
        DialogManager.showErrorDialog(this);
    }

    @Override public void scrollToTop() {
        binding.rcvQuestion.scrollToPosition(0);
    }
}
