package com.minilook.minilook.ui.order_history;

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
import com.minilook.minilook.data.model.order.OrderHistoryDataModel;
import com.minilook.minilook.ui.base._BaseActivity;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.base.widget.BottomBar;
import com.minilook.minilook.ui.main.MainActivity;
import com.minilook.minilook.ui.order_detail.OrderDetailActivity;
import com.minilook.minilook.ui.order_history.adapter.OrderHistoryAdapter;
import com.minilook.minilook.ui.order_history.di.OrderHistoryArguments;

public class OrderHistoryActivity extends _BaseActivity implements OrderHistoryPresenter.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, OrderHistoryActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    @BindView(R.id.rcv_order) RecyclerView recyclerView;
    @BindView(R.id.layout_empty_panel) LinearLayout emptyPanel;

    @BindDimen(R.dimen.dp_6) int dp_6;

    private OrderHistoryPresenter presenter;
    private OrderHistoryAdapter adapter = new OrderHistoryAdapter();
    private BaseAdapterDataView<OrderHistoryDataModel> adapterView = adapter;

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
            .adapter(adapter)
            .build();
    }

    @Override public void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        DividerDecoration.builder(this)
            .size(dp_6)
            .asSpace()
            .showFirstDivider()
            .build()
            .addTo(recyclerView);
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

    @Override public void navigateToOrderDetail(String order_id, String receipt_id) {
        OrderDetailActivity.start(this, order_id, receipt_id);
    }

    @OnClick(R.id.txt_empty)
    void onEmptyClick() {
        MainActivity.start(this, BottomBar.POSITION_MARKET);
    }
}
