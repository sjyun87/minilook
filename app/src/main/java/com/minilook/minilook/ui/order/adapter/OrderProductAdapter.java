package com.minilook.minilook.ui.order.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.minilook.minilook.data.model.shopping.ShoppingProductDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.order.viewholder.OrderProductItemVH;
import java.util.ArrayList;
import java.util.List;

public class OrderProductAdapter extends RecyclerView.Adapter<OrderProductItemVH>
    implements BaseAdapterDataModel<ShoppingProductDataModel>, BaseAdapterDataView<ShoppingProductDataModel> {

    private List<ShoppingProductDataModel> items = new ArrayList<>();

    @NonNull @Override public OrderProductItemVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderProductItemVH(parent);
    }

    @Override public void onBindViewHolder(@NonNull OrderProductItemVH holder, int position) {
        holder.bind(items.get(position));
    }

    @Override public int getItemCount() {
        return getSize();
    }

    @Override public void add(ShoppingProductDataModel $item) {
        items.add($item);
    }

    @Override public void add(int $index, ShoppingProductDataModel $item) {
        items.add($index, $item);
    }

    @Override public void addAll(List<ShoppingProductDataModel> $items) {
        items.addAll($items);
    }

    @Override public void set(int $index, ShoppingProductDataModel $item) {
        items.set($index, $item);
    }

    @Override public void set(List<ShoppingProductDataModel> $items) {
        items.clear();
        items.addAll($items);
    }

    @Override public ShoppingProductDataModel get(int $index) {
        return items.get($index);
    }

    @Override public List<ShoppingProductDataModel> get() {
        return items;
    }

    @Override public int get(ShoppingProductDataModel $item) {
        return items.indexOf($item);
    }

    @Override public void remove(int $index) {
        items.remove($index);
    }

    @Override public void remove(ShoppingProductDataModel $item) {
        items.remove($item);
    }

    @Override public void removeAll() {
        items.clear();
    }

    @Override public void clear() {
        items.clear();
    }

    @Override public int getSize() {
        return items.size();
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
