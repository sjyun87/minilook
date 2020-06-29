package com.minilook.minilook.ui.main.fragment.lookbook.view.detail.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.main.fragment.lookbook.view.detail.viewholder.LookBookProductVH;

import java.util.ArrayList;
import java.util.List;

public class LookBookProductAdapter extends RecyclerView.Adapter<LookBookProductVH>
    implements BaseAdapterDataModel<ProductDataModel>, BaseAdapterDataView<ProductDataModel> {

    private List<ProductDataModel> items = new ArrayList<>();

    @NonNull @Override public LookBookProductVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LookBookProductVH(parent);
    }

    @Override public void onBindViewHolder(@NonNull LookBookProductVH holder, int position) {
        holder.bind(items.get(position));
    }

    @Override public int getItemCount() {
        return getSize();
    }

    @Override public void add(ProductDataModel $item) {
        this.items.add($item);
    }

    @Override public void add(int $index, ProductDataModel $item) {
        this.items.add($index, $item);
    }

    @Override public void addAll(List<ProductDataModel> $items) {
        this.items.addAll($items);
    }

    @Override public void set(int $index, ProductDataModel $item) {
        this.items.set($index, $item);
    }

    @Override public void set(List<ProductDataModel> $items) {
        this.items.clear();
        this.items.addAll($items);
    }

    @Override public ProductDataModel get(int $index) {
        return this.items.get($index);
    }

    @Override public List<ProductDataModel> get() {
        return this.items;
    }

    @Override public void remove(int $index) {
        this.items.remove($index);
    }

    @Override public void remove(ProductDataModel $item) {
        this.remove($item);
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
