package com.minilook.minilook.ui.brand.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.minilook.minilook.data.model.brand.BrandDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.brand.viewholder.BrandItemVH;
import java.util.ArrayList;
import java.util.List;

public class BrandAdapter extends RecyclerView.Adapter<BrandItemVH> implements
    BaseAdapterDataModel<BrandDataModel>, BaseAdapterDataView<BrandDataModel> {

    private final List<BrandDataModel> items = new ArrayList<>();

    @NonNull @Override
    public BrandItemVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BrandItemVH(parent);
    }

    @Override public void onBindViewHolder(@NonNull BrandItemVH holder, int position) {
        holder.bind(items.get(position));
    }

    @Override public int getItemCount() {
        return getSize();
    }

    @Override public void add(BrandDataModel $item) {
        items.add($item);
        items.add($item);
    }

    @Override public void add(int $index, BrandDataModel $item) {
        items.add($index, $item);
    }

    @Override public void addAll(List<BrandDataModel> $items) {
        items.addAll($items);
    }

    @Override public void set(int $index, BrandDataModel $item) {
        items.set($index, $item);
    }

    @Override public void set(List<BrandDataModel> $items) {
        items.clear();
        items.addAll($items);
    }

    @Override public BrandDataModel get(int $index) {
        return items.get($index);
    }

    @Override public List<BrandDataModel> get() {
        return items;
    }

    @Override public int get(BrandDataModel $item) {
        return items.indexOf($item);
    }

    @Override public void remove(int $index) {
        items.remove($index);
    }

    @Override public void remove(BrandDataModel $item) {
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
