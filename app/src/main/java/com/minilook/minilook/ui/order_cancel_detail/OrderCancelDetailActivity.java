package com.minilook.minilook.ui.order_cancel_detail;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.base._BaseActivity;
import com.minilook.minilook.ui.order_cancel_detail.di.OrderCancelDetailArguments;

public class OrderCancelDetailActivity extends _BaseActivity implements OrderCancelDetailPresenter.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, OrderCancelDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    @BindView(R.id.rcv_order) RecyclerView recyclerView;

    private OrderCancelDetailPresenter presenter;

    @Override protected int getLayoutID() {
        return R.layout.activity_order_cancel_detail;
    }

    @Override protected void createPresenter() {
        presenter = new OrderCancelDetailPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private OrderCancelDetailArguments provideArguments() {
        return OrderCancelDetailArguments.builder()
            .view(this)
            .build();
    }

    @Override public void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
