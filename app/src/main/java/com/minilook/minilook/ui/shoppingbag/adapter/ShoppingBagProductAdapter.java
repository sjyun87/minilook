package com.minilook.minilook.ui.shoppingbag.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.minilook.minilook.data.model.order.OrderProductDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.shoppingbag.viewholder.ShoppingBagProductItemVH;
import java.util.ArrayList;
import java.util.List;

public class ShoppingBagProductAdapter extends RecyclerView.Adapter<ShoppingBagProductItemVH>
    implements BaseAdapterDataModel<OrderProductDataModel>, BaseAdapterDataView<OrderProductDataModel> {

    private List<OrderProductDataModel> items = new ArrayList<>();

    @NonNull @Override public ShoppingBagProductItemVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ShoppingBagProductItemVH(parent);
    }

    @Override public void onBindViewHolder(@NonNull ShoppingBagProductItemVH holder, int position) {
        holder.bind(items.get(position));
    }

    @Override public int getItemCount() {
        return getSize();
    }

    @Override public void add(OrderProductDataModel $item) {
        this.items.add($item);
    }

    @Override public void add(int $index, OrderProductDataModel $item) {
        this.items.add($index, $item);
    }

    @Override public void addAll(List<OrderProductDataModel> $items) {
        this.items.addAll($items);
    }

    @Override public void set(int $index, OrderProductDataModel $item) {
        this.items.set($index, $item);
    }

    @Override public void set(List<OrderProductDataModel> $items) {
        this.items.clear();
        this.items.addAll($items);
    }

    @Override public OrderProductDataModel get(int $index) {
        return this.items.get($index);
    }

    @Override public List<OrderProductDataModel> get() {
        return this.items;
    }

    @Override public int get(OrderProductDataModel $item) {
        return items.indexOf($item);
    }

    @Override public void remove(int $index) {
        this.items.remove($index);
    }

    @Override public void remove(OrderProductDataModel $item) {
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
