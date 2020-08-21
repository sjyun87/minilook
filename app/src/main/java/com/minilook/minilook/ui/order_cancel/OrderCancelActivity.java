package com.minilook.minilook.ui.order_cancel;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.order_cancel.di.OrderCancelArguments;

public class OrderCancelActivity extends BaseActivity implements OrderCancelPresenter.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, OrderCancelActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    @BindView(R.id.rcv_order) RecyclerView recyclerView;

    private OrderCancelPresenter presenter;

    @Override protected int getLayoutID() {
        return R.layout.activity_order_cancel;
    }

    @Override protected void createPresenter() {
        presenter = new OrderCancelPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private OrderCancelArguments provideArguments() {
        return OrderCancelArguments.builder()
            .view(this)
            .build();
    }

    @Override public void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
