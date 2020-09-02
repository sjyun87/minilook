package com.minilook.minilook.ui.recent;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.product.adapter.ProductAdapter;
import com.minilook.minilook.ui.recent.di.RecentArguments;

public class RecentActivity extends BaseActivity implements RecentPresenter.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, RecentActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    @BindView(R.id.rcv_goods) RecyclerView recyclerView;

    private RecentPresenter presenter;
    private ProductAdapter adapter = new ProductAdapter();
    private BaseAdapterDataView<ProductDataModel> adapterView = adapter;

    @Override protected int getLayoutID() {
        return R.layout.activity_recent;
    }

    @Override protected void createPresenter() {
        presenter = new RecentPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private RecentArguments provideArguments() {
        return RecentArguments.builder()
            .view(this)
            .adapter(adapter)
            .build();
    }

    @Override public void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setViewType(ProductAdapter.VIEW_TYPE_WIDE);
        recyclerView.setAdapter(adapter);
    }

    @Override public void refresh(int start, int end) {
        adapterView.refresh(start, end);
    }
}
