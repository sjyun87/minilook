package com.minilook.minilook.ui.order_history.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.minilook.minilook.data.model.order.OrderHistoryDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.order_history.viewholder.OrderHistoryItemVH;
import java.util.ArrayList;
import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryItemVH> implements
    BaseAdapterDataModel<OrderHistoryDataModel>, BaseAdapterDataView<OrderHistoryDataModel> {

    private List<OrderHistoryDataModel> items = new ArrayList<>();

    @NonNull @Override public OrderHistoryItemVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderHistoryItemVH(parent);
    }

    @Override public void onBindViewHolder(@NonNull OrderHistoryItemVH holder, int position) {
        holder.bind(items.get(position));
    }

    @Override public int getItemCount() {
        return getSize();
    }

    @Override public void add(OrderHistoryDataModel $item) {
        this.items.add($item);
    }

    @Override public void add(int $index, OrderHistoryDataModel $item) {
        this.items.add($index, $item);
    }

    @Override public void addAll(List<OrderHistoryDataModel> $items) {
        this.items.addAll($items);
    }

    @Override public void set(int $index, OrderHistoryDataModel $item) {
        this.items.set($index, $item);
    }

    @Override public void set(List<OrderHistoryDataModel> $items) {
        this.items.clear();
        this.items.addAll($items);
    }

    @Override public OrderHistoryDataModel get(int $index) {
        return this.items.get($index);
    }

    @Override public List<OrderHistoryDataModel> get() {
        return this.items;
    }

    @Override public int get(OrderHistoryDataModel $item) {
        return items.indexOf($item);
    }

    @Override public void remove(int $index) {
        this.items.remove($index);
    }

    @Override public void remove(OrderHistoryDataModel $item) {
        this.items.remove($item);
    }

    @Override public void removeAll() {
        this.items.clear();
    }

    @Override public void clear() {
        this.items.clear();
    }

    @Override public int getSize() {
        return this.items.size();
    }

    @Override public void refresh() {
        notifyDataSetChanged();
    }

    @Override public void refresh(int $position) {
        notifyItemChanged($position);
    }

    @Override public void refresh(int $start, int $row) {
        notifyItemRangeChanged($start, $row);
    }
}
