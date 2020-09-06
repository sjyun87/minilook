package com.minilook.minilook.ui.product_bridge.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.minilook.minilook.data.model.search.OptionMenuDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.product_bridge.viewholder.ProductBridgeOptionVH;
import java.util.ArrayList;
import java.util.List;
import lombok.Setter;

public class ProductBridgeOptionAdapter extends RecyclerView.Adapter<ProductBridgeOptionVH> implements
    BaseAdapterDataModel<OptionMenuDataModel>, BaseAdapterDataView<OptionMenuDataModel> {

    private List<OptionMenuDataModel> items = new ArrayList<>();
    @Setter private ProductBridgeOptionVH.OnMenuClickListener onMenuClickListener;

    @NonNull @Override
    public ProductBridgeOptionVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductBridgeOptionVH(parent);
    }

    @Override public void onBindViewHolder(@NonNull ProductBridgeOptionVH holder, int position) {
        holder.bind(items.get(position));
        holder.setOnMenuClickListener(onMenuClickListener);
    }

    @Override public int getItemCount() {
        return getSize();
    }

    @Override public void add(OptionMenuDataModel $item) {
        this.items.add($item);
    }

    @Override public void add(int $index, OptionMenuDataModel $item) {
        this.items.add($index, $item);
    }

    @Override public void addAll(List<OptionMenuDataModel> $items) {
        this.items.addAll($items);
    }

    @Override public void set(int $index, OptionMenuDataModel $item) {
        this.items.set($index, $item);
    }

    @Override public void set(List<OptionMenuDataModel> $items) {
        this.items.clear();
        this.items.addAll($items);
    }

    @Override public OptionMenuDataModel get(int $index) {
        return this.items.get($index);
    }

    @Override public List<OptionMenuDataModel> get() {
        return this.items;
    }

    @Override public int get(OptionMenuDataModel $item) {
        return items.indexOf($item);
    }

    @Override public void remove(int $index) {
        this.items.remove($index);
    }

    @Override public void remove(OptionMenuDataModel $item) {
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
