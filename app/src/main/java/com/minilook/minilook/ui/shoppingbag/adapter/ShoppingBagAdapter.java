package com.minilook.minilook.ui.shoppingbag.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.minilook.minilook.data.model.shopping.ShoppingBrandDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.shoppingbag.viewholder.ShoppingBagItemVH;
import java.util.ArrayList;
import java.util.List;

public class ShoppingBagAdapter extends RecyclerView.Adapter<ShoppingBagItemVH>
    implements BaseAdapterDataModel<ShoppingBrandDataModel>, BaseAdapterDataView<ShoppingBrandDataModel> {

    private List<ShoppingBrandDataModel> items = new ArrayList<>();

    @NonNull @Override public ShoppingBagItemVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ShoppingBagItemVH(parent);
    }

    @Override public void onBindViewHolder(@NonNull ShoppingBagItemVH holder, int position) {
        holder.bind(items.get(position));
    }

    @Override public int getItemCount() {
        return getSize();
    }

    @Override public void add(ShoppingBrandDataModel $item) {
        items.add($item);
    }

    @Override public void add(int $index, ShoppingBrandDataModel $item) {
        items.add($index, $item);
    }

    @Override public void addAll(List<ShoppingBrandDataModel> $items) {
        items.addAll($items);
    }

    @Override public void set(int $index, ShoppingBrandDataModel $item) {
        items.set($index, $item);
    }

    @Override public void set(List<ShoppingBrandDataModel> $items) {
        items.clear();
        items.addAll($items);
    }

    @Override public ShoppingBrandDataModel get(int $index) {
        return items.get($index);
    }

    @Override public List<ShoppingBrandDataModel> get() {
        return items;
    }

    @Override public int get(ShoppingBrandDataModel $item) {
        return items.indexOf($item);
    }

    @Override public void remove(int $index) {
        items.remove($index);
    }

    @Override public void remove(ShoppingBrandDataModel $item) {
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
        notifyItemRangeInserted($start, $row);
    }
}
