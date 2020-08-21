package com.minilook.minilook.ui.order_detail;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.order_detail.di.OrderDetailArguments;

public class OrderDetailActivity extends BaseActivity implements OrderDetailPresenter.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, OrderDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    @BindView(R.id.rcv_brand_order) RecyclerView recyclerView;

    private OrderDetailPresenter presenter;

    @Override protected int getLayoutID() {
        return R.layout.activity_order_detail;
    }

    @Override protected void createPresenter() {
        presenter = new OrderDetailPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private OrderDetailArguments provideArguments() {
        return OrderDetailArguments.builder()
            .view(this)
            .build();
    }

    @Override public void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
