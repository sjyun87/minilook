package com.minilook.minilook.ui.order_cancel.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.minilook.minilook.data.model.order.OrderGoodsDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.order_cancel.viewholder.OrderCancelItemVH;
import java.util.ArrayList;
import java.util.List;

public class OrderCancelAdapter extends RecyclerView.Adapter<OrderCancelItemVH>
    implements BaseAdapterDataModel<OrderGoodsDataModel>, BaseAdapterDataView<OrderGoodsDataModel> {

    private List<OrderGoodsDataModel> items = new ArrayList<>();

    @NonNull @Override public OrderCancelItemVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderCancelItemVH(parent);
    }

    @Override public void onBindViewHolder(@NonNull OrderCancelItemVH holder, int position) {
        holder.bind(items.get(position));
    }

    @Override public int getItemCount() {
        return getSize();
    }

    @Override public void add(OrderGoodsDataModel $item) {
        this.items.add($item);
    }

    @Override public void add(int $index, OrderGoodsDataModel $item) {
        this.items.add($index, $item);
    }

    @Override public void addAll(List<OrderGoodsDataModel> $items) {
        this.items.addAll($items);
    }

    @Override public void set(int $index, OrderGoodsDataModel $item) {
        this.items.set($index, $item);
    }

    @Override public void set(List<OrderGoodsDataModel> $items) {
        this.items.clear();
        this.items.addAll($items);
    }

    @Override public OrderGoodsDataModel get(int $index) {
        return this.items.get($index);
    }

    @Override public List<OrderGoodsDataModel> get() {
        return this.items;
    }

    @Override public int get(OrderGoodsDataModel $item) {
        return items.indexOf($item);
    }

    @Override public void remove(int $index) {
        this.items.remove($index);
    }

    @Override public void remove(OrderGoodsDataModel $item) {
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
