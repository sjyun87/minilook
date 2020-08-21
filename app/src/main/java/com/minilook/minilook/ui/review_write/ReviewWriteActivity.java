package com.minilook.minilook.ui.review_write;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.product.adapter.ProductAdapter;
import com.minilook.minilook.ui.review_write.di.ReviewWriteArguments;

public class ReviewWriteActivity extends BaseActivity implements ReviewWritePresenter.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, ReviewWriteActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }


    private ReviewWritePresenter presenter;

    @Override protected int getLayoutID() {
        return R.layout.activity_review_write;
    }

    @Override protected void createPresenter() {
        presenter = new ReviewWritePresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private ReviewWriteArguments provideArguments() {
        return ReviewWriteArguments.builder()
            .view(this)
            .build();
    }
}
