package com.minilook.minilook.ui.review;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindDimen;
import butterknife.BindView;
import com.fondesa.recyclerviewdivider.DividerDecoration;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.review.ReviewDataModel;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.base.listener.EndlessOnScrollListener;
import com.minilook.minilook.ui.base.widget.TitleBar;
import com.minilook.minilook.ui.review.adapter.ReviewAdapter;
import com.minilook.minilook.ui.review.di.ReviewArguments;

public class ReviewActivity extends BaseActivity implements ReviewPresenter.View {

    public static void start(Context context, int productNo) {
        Intent intent = new Intent(context, ReviewActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("productNo", productNo);
        context.startActivity(intent);
    }

    @BindView(R.id.titlebar) TitleBar titleBar;
    @BindView(R.id.rcv_review) RecyclerView recyclerView;
    @BindView(R.id.txt_empty) TextView emptyTextView;

    @BindDimen(R.dimen.dp_1) int dp_1;

    private ReviewPresenter presenter;
    private ReviewAdapter adapter = new ReviewAdapter();
    private BaseAdapterDataView<ReviewDataModel> adapterView = adapter;

    @Override protected int getLayoutID() {
        return R.layout.activity_review;
    }

    @Override protected void createPresenter() {
        presenter = new ReviewPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private ReviewArguments provideArguments() {
        return ReviewArguments.builder()
            .view(this)
            .productNo(getIntent().getIntExtra("productNo", -1))
            .adapter(adapter)
            .build();
    }

    @Override public void setTotalCount(int count) {
        titleBar.setCount(count);
        titleBar.setShowCount(true);
    }

    @Override public void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        DividerDecoration.builder(this)
            .size(dp_1)
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

    @Override public void emptyPanel() {
        emptyTextView.setVisibility(View.VISIBLE);
    }
}
