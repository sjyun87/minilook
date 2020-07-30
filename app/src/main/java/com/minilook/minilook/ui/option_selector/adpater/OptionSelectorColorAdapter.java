package com.minilook.minilook.ui.option_selector.adpater;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.minilook.minilook.data.model.product.ProductColorDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.option_selector.viewholder.OptionSelectorColorVH;
import java.util.ArrayList;
import java.util.List;
import lombok.Setter;

public class OptionSelectorColorAdapter extends RecyclerView.Adapter<OptionSelectorColorVH>
    implements BaseAdapterDataModel<ProductColorDataModel>, BaseAdapterDataView<ProductColorDataModel> {

    private List<ProductColorDataModel> items = new ArrayList<>();
    @Setter private OptionSelectorColorVH.OnColorSelectedListener onColorSelectedListener;

    @NonNull @Override public OptionSelectorColorVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OptionSelectorColorVH(parent);
    }

    @Override public void onBindViewHolder(@NonNull OptionSelectorColorVH holder, int position) {
        holder.bind(items.get(position));
        holder.setOnColorSelectedListener(onColorSelectedListener);
    }

    @Override public int getItemCount() {
        return getSize();
    }

    @Override public void add(ProductColorDataModel $item) {
        this.items.add($item);
    }

    @Override public void add(int $index, ProductColorDataModel $item) {
        this.items.add($index, $item);
    }

    @Override public void addAll(List<ProductColorDataModel> $items) {
        this.items.addAll($items);
    }

    @Override public void set(int $index, ProductColorDataModel $item) {
        this.items.set($index, $item);
    }

    @Override public void set(List<ProductColorDataModel> $items) {
        this.items.clear();
        this.items.addAll($items);
    }

    @Override public ProductColorDataModel get(int $index) {
        return this.items.get($index);
    }

    @Override public List<ProductColorDataModel> get() {
        return this.items;
    }

    @Override public void remove(int $index) {
        this.items.remove($index);
    }

    @Override public void remove(ProductColorDataModel $item) {
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
