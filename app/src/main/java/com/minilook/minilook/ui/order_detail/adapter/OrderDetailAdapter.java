package com.minilook.minilook.ui.order_detail.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.minilook.minilook.data.model.order.OrderBrandDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.order_detail.viewholder.OrderDetailItemVH;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailItemVH>
    implements BaseAdapterDataModel<OrderBrandDataModel>, BaseAdapterDataView<OrderBrandDataModel> {

    private List<OrderBrandDataModel> items = new ArrayList<>();

    @NonNull @Override public OrderDetailItemVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderDetailItemVH(parent);
    }

    @Override public void onBindViewHolder(@NonNull OrderDetailItemVH holder, int position) {
        holder.bind(items.get(position));
    }

    @Override public int getItemCount() {
        return getSize();
    }

    @Override public void add(OrderBrandDataModel $item) {
        this.items.add($item);
    }

    @Override public void add(int $index, OrderBrandDataModel $item) {
        this.items.add($index, $item);
    }

    @Override public void addAll(List<OrderBrandDataModel> $items) {
        this.items.addAll($items);
    }

    @Override public void set(int $index, OrderBrandDataModel $item) {
        this.items.set($index, $item);
    }

    @Override public void set(List<OrderBrandDataModel> $items) {
        this.items.clear();
        this.items.addAll($items);
    }

    @Override public OrderBrandDataModel get(int $index) {
        return this.items.get($index);
    }

    @Override public List<OrderBrandDataModel> get() {
        return this.items;
    }

    @Override public int get(OrderBrandDataModel $item) {
        return items.indexOf($item);
    }

    @Override public void remove(int $index) {
        this.items.remove($index);
    }

    @Override public void remove(OrderBrandDataModel $item) {
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
