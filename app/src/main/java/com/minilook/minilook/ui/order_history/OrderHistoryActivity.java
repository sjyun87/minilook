package com.minilook.minilook.ui.order_history;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.order_history.di.OrderHistoryArguments;

public class OrderHistoryActivity extends BaseActivity implements OrderHistoryPresenter.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, OrderHistoryActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    @BindView(R.id.rcv_order) RecyclerView recyclerView;

    private OrderHistoryPresenter presenter;

    @Override protected int getLayoutID() {
        return R.layout.activity_order_history;
    }

    @Override protected void createPresenter() {
        presenter = new OrderHistoryPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private OrderHistoryArguments provideArguments() {
        return OrderHistoryArguments.builder()
            .view(this)
            .build();
    }

    @Override public void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
