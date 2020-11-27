package com.minilook.minilook.ui.order_cancel;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.order.OrderCancelDataModel;
import com.minilook.minilook.data.model.order.OrderProductDataModel;
import com.minilook.minilook.ui.base._BaseActivity;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.order_cancel.adapter.OrderCancelAdapter;
import com.minilook.minilook.ui.order_cancel.di.OrderCancelArguments;

public class OrderCancelActivity extends _BaseActivity implements OrderCancelPresenter.View {

    public static void start(Context context, OrderCancelDataModel orderData) {
        Intent intent = new Intent(context, OrderCancelActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("orderData", orderData);
        context.startActivity(intent);
    }

    @BindView(R.id.txt_info_number) TextView orderNoTextView;
    @BindView(R.id.txt_info_date) TextView orderDateTextView;
    @BindView(R.id.rcv_goods) RecyclerView recyclerView;

    @BindString(R.string.toast_receipt_completed) String toast_receipt_complete;

    private OrderCancelPresenter presenter;
    private OrderCancelAdapter adapter = new OrderCancelAdapter();
    private BaseAdapterDataView<OrderProductDataModel> adapterView = adapter;

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
            .orderData((OrderCancelDataModel) getIntent().getSerializableExtra("orderData"))
            .adapter(adapter)
            .build();
    }

    @Override public void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override public void refresh() {
        adapterView.refresh();
    }

    @Override public void setOrderNo(String no) {
        orderNoTextView.setText(no);
    }

    @Override public void setOrderDate(String date) {
        orderDateTextView.setText(date);
    }

    @Override public void showReceiptCompletedToast() {
        Toast.makeText(this, toast_receipt_complete, Toast.LENGTH_SHORT).show();
    }

    @Override public void showErrorToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.txt_apply)
    void onApplyClick() {
        presenter.onApplyClick();
    }
}
