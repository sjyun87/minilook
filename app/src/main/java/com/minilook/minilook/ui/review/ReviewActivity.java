package com.minilook.minilook.ui.review;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.product.adapter.ProductAdapter;
import com.minilook.minilook.ui.review.di.ReviewArguments;

public class ReviewActivity extends BaseActivity implements ReviewPresenter.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, ReviewActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    @BindView(R.id.rcv_product) RecyclerView recyclerView;

    private ReviewPresenter presenter;
    private ProductAdapter adapter;

    @Override protected int getLayoutID() {
        return R.layout.activity_recent;
    }

    @Override protected void createPresenter() {
        presenter = new ReviewPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private ReviewArguments provideArguments() {
        return ReviewArguments.builder()
            .view(this)
            .build();
    }

    @Override public void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setViewType(ProductAdapter.VIEW_TYPE_WIDE);
        recyclerView.setAdapter(adapter);
    }
}
