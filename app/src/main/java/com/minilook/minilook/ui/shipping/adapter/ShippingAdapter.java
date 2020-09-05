package com.minilook.minilook.ui.shipping.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.minilook.minilook.data.model.shipping.ShippingDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.shipping.viewholder.ShippingItemVH;
import java.util.ArrayList;
import java.util.List;

public class ShippingAdapter extends RecyclerView.Adapter<ShippingItemVH> implements
    BaseAdapterDataModel<ShippingDataModel>, BaseAdapterDataView<ShippingDataModel> {

    private List<ShippingDataModel> items = new ArrayList<>();

    @NonNull @Override
    public ShippingItemVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ShippingItemVH(parent);
    }

    @Override public void onBindViewHolder(@NonNull ShippingItemVH holder, int position) {
        holder.bind(items.get(position));
    }

    @Override public int getItemCount() {
        return getSize();
    }

    @Override public void add(ShippingDataModel $item) {
        this.items.add($item);
    }

    @Override public void add(int $index, ShippingDataModel $item) {
        this.items.add($index, $item);
    }

    @Override public void addAll(List<ShippingDataModel> $items) {
        this.items.addAll($items);
    }

    @Override public void set(int $index, ShippingDataModel $item) {
        this.items.set($index, $item);
    }

    @Override public void set(List<ShippingDataModel> $items) {
        this.items.clear();
        this.items.addAll($items);
    }

    @Override public ShippingDataModel get(int $index) {
        return this.items.get($index);
    }

    @Override public List<ShippingDataModel> get() {
        return this.items;
    }

    @Override public int get(ShippingDataModel $item) {
        return items.indexOf($item);
    }

    @Override public void remove(int $index) {
        this.items.remove($index);
    }

    @Override public void remove(ShippingDataModel $item) {
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
