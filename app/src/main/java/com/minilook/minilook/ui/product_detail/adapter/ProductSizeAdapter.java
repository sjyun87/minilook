package com.minilook.minilook.ui.product_detail.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.minilook.minilook.data.model.base.SizeDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.product_detail.viewholder.ProductSizeVH;
import java.util.ArrayList;
import java.util.List;

public class ProductSizeAdapter extends RecyclerView.Adapter<ProductSizeVH>
    implements BaseAdapterDataModel<SizeDataModel>, BaseAdapterDataView<SizeDataModel> {

    private List<SizeDataModel> items = new ArrayList<>();

    @NonNull @Override public ProductSizeVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductSizeVH(parent);
    }

    @Override public void onBindViewHolder(@NonNull ProductSizeVH holder, int position) {
        holder.bind(items.get(position));
    }

    @Override public int getItemCount() {
        return getSize();
    }

    @Override public void add(SizeDataModel $item) {
        this.items.add($item);
    }

    @Override public void add(int $index, SizeDataModel $item) {
        this.items.add($index, $item);
    }

    @Override public void addAll(List<SizeDataModel> $items) {
        this.items.addAll($items);
    }

    @Override public void set(int $index, SizeDataModel $item) {
        this.items.set($index, $item);
    }

    @Override public void set(List<SizeDataModel> $items) {
        this.items.clear();
        this.items.addAll($items);
    }

    @Override public SizeDataModel get(int $index) {
        return this.items.get($index);
    }

    @Override public List<SizeDataModel> get() {
        return this.items;
    }

    @Override public void remove(int $index) {
        this.items.remove($index);
    }

    @Override public void remove(SizeDataModel $item) {
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
