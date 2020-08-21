package com.minilook.minilook.ui.order_detail.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.common.SortDataModel;
import com.minilook.minilook.ui.base.BaseViewHolder;

public class OrderHistoryItemVH extends BaseViewHolder<SortDataModel> {

    @BindView(R.id.rcv_goods) RecyclerView recyclerView;

    public OrderHistoryItemVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_order_history, (ViewGroup) itemView, false));

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override public void bind(SortDataModel $data) {
        super.bind($data);
    }
}
