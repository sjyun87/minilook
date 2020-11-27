package com.minilook.minilook.ui.order_history.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.order.OrderHistoryDataModel;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.ui.base._BaseViewHolder;
import com.minilook.minilook.ui.order_history.OrderHistoryPresenterImpl;
import com.minilook.minilook.ui.product.adapter.ProductAdapter;

public class OrderHistoryItemVH extends _BaseViewHolder<OrderHistoryDataModel> {

    @BindView(R.id.txt_order_number) TextView orderNumberTextView;
    @BindView(R.id.txt_order_date) TextView orderDateTextView;
    @BindView(R.id.rcv_product) RecyclerView recyclerView;

    private ProductAdapter adapter;

    public OrderHistoryItemVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_order_history, (ViewGroup) itemView, false));

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        adapter = new ProductAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter.setViewType(ProductAdapter.VIEW_TYPE_WIDE_32);
        recyclerView.setAdapter(adapter);
    }

    @Override public void bind(OrderHistoryDataModel $data) {
        super.bind($data);

        orderNumberTextView.setText(data.getOrderNo());
        orderDateTextView.setText(data.getOrderDate());

        adapter.set(data.getProducts());
        adapter.refresh();

        itemView.setOnClickListener(this::onItemClick);
    }

    void onItemClick(View view) {
        //RxBus.send(new OrderHistoryPresenterImpl.RxBusEventOrderClick(data));
    }

    @OnClick(R.id.curtain)
    void onCurtainClick() {
        RxBus.send(new OrderHistoryPresenterImpl.RxBusEventOrderClick(data));
    }
}
