package com.minilook.minilook.ui.question;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.OnClick;
import com.fondesa.recyclerviewdivider.DividerDecoration;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.question.QuestionDataModel;
import com.minilook.minilook.ui.base._BaseActivity;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.base.listener.EndlessOnScrollListener;
import com.minilook.minilook.ui.base.widget.TitleBar;
import com.minilook.minilook.ui.login.LoginActivity;
import com.minilook.minilook.ui.question.adapter.QuestionAdapter;
import com.minilook.minilook.ui.question.di.QuestionArguments;
import com.minilook.minilook.ui.question_write.QuestionWriteActivity;

public class QuestionActivity extends _BaseActivity implements QuestionPresenter.View {

    public static void start(Context context, int productNo) {
        Intent intent = new Intent(context, QuestionActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("productNo", productNo);
        context.startActivity(intent);
    }

    @BindView(R.id.titlebar) TitleBar titleBar;
    @BindView(R.id.rcv_question) RecyclerView recyclerView;
    @BindView(R.id.layout_empty_panel) LinearLayout emptyPanel;

    @BindDimen(R.dimen.dp_4) int dp_4;

    private QuestionPresenter presenter;
    private QuestionAdapter adapter = new QuestionAdapter();
    private BaseAdapterDataView<QuestionDataModel> adapterView = adapter;

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
            .productNo(getIntent().getIntExtra("productNo", -1))
            .adapter(adapter)
            .build();
    }

    @Override public void setupTitleBar(int productNo) {
        titleBar.setProductNo(productNo);
    }

    @Override public void setTotalCount(int count) {
        titleBar.setCount(count);
        titleBar.setShowCount(true);
    }

    @Override public void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        DividerDecoration.builder(this)
            .size(dp_4)
            .asSpace()
            .build()
            .addTo(recyclerView);
        EndlessOnScrollListener scrollListener =
            EndlessOnScrollListener.builder()
                .layoutManager(recyclerView.getLayoutManager())
                .onLoadMoreListener(presenter::onLoadMore)
                .visibleThreshold(10)
                .build();
        recyclerView.addOnScrollListener(scrollListener);
    }

    @Override public void refresh() {
        adapterView.refresh();
    }

    @Override public void refresh(int start, int rows) {
        adapterView.refresh(start, rows);
    }

    @Override public void showEmptyPanel() {
        emptyPanel.setVisibility(View.VISIBLE);
    }

    @Override public void hideEmptyPanel() {
        emptyPanel.setVisibility(View.GONE);
    }

    @Override public void navigateToQuestionWrite(int productNo) {
        QuestionWriteActivity.start(this, productNo);
    }

    @Override public void navigateToLogin() {
        LoginActivity.start(this);
    }

    @OnClick(R.id.txt_empty)
    void onEmptyClick() {
        presenter.onEmptyClick();
    }
}
