package com.minilook.minilook.ui.product_bridge.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.minilook.minilook.data.model.common.SortDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.product_bridge.viewholder.ProductBridgeSortItemVH;
import java.util.ArrayList;
import java.util.List;
import lombok.Setter;

public class ProductBridgeSortAdapter extends RecyclerView.Adapter<ProductBridgeSortItemVH> implements
    BaseAdapterDataModel<SortDataModel>, BaseAdapterDataView<SortDataModel> {

    private List<SortDataModel> items = new ArrayList<>();
    @Setter private ProductBridgeSortItemVH.OnSortSelectListener onSortSelectListener;

    @NonNull @Override public ProductBridgeSortItemVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductBridgeSortItemVH(parent);
    }

    @Override public void onBindViewHolder(@NonNull ProductBridgeSortItemVH holder, int position) {
        holder.bind(items.get(position));
        holder.setOnSortSelectListener(onSortSelectListener);
    }

    @Override public int getItemCount() {
        return getSize();
    }

    @Override public void add(SortDataModel $item) {
        this.items.add($item);
    }

    @Override public void add(int $index, SortDataModel $item) {
        this.items.add($index, $item);
    }

    @Override public void addAll(List<SortDataModel> $items) {
        this.items.addAll($items);
    }

    @Override public void set(int $index, SortDataModel $item) {
        this.items.set($index, $item);
    }

    @Override public void set(List<SortDataModel> $items) {
        this.items.clear();
        this.items.addAll($items);
    }

    @Override public SortDataModel get(int $index) {
        return this.items.get($index);
    }

    @Override public List<SortDataModel> get() {
        return this.items;
    }

    @Override public int get(SortDataModel $item) {
        return items.indexOf($item);
    }

    @Override public void remove(int $index) {
        this.items.remove($index);
    }

    @Override public void remove(SortDataModel $item) {
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
