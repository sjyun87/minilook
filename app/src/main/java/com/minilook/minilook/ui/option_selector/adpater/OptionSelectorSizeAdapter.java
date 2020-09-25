package com.minilook.minilook.ui.option_selector.adpater;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.minilook.minilook.data.model.product.ProductSizeDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.option_selector.viewholder.OptionSelectorSizeVH;
import java.util.ArrayList;
import java.util.List;
import lombok.Setter;

public class OptionSelectorSizeAdapter extends RecyclerView.Adapter<OptionSelectorSizeVH>
    implements BaseAdapterDataModel<ProductSizeDataModel>, BaseAdapterDataView<ProductSizeDataModel> {

    private List<ProductSizeDataModel> items = new ArrayList<>();
    @Setter private OptionSelectorSizeVH.OnSizeSelectedListener onSizeSelectedListener;

    @NonNull @Override public OptionSelectorSizeVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OptionSelectorSizeVH(parent);
    }

    @Override public void onBindViewHolder(@NonNull OptionSelectorSizeVH holder, int position) {
        holder.bind(items.get(position));
        holder.setOnSizeSelectedListener(onSizeSelectedListener);
    }

    @Override public int getItemCount() {
        return getSize();
    }

    @Override public void add(ProductSizeDataModel $item) {
        items.add($item);
    }

    @Override public void add(int $index, ProductSizeDataModel $item) {
        items.add($index, $item);
    }

    @Override public void addAll(List<ProductSizeDataModel> $items) {
        items.addAll($items);
    }

    @Override public void set(int $index, ProductSizeDataModel $item) {
        items.set($index, $item);
    }

    @Override public void set(List<ProductSizeDataModel> $items) {
        items.clear();
        items.addAll($items);
    }

    @Override public ProductSizeDataModel get(int $index) {
        return items.get($index);
    }

    @Override public List<ProductSizeDataModel> get() {
        return items;
    }

    @Override public int get(ProductSizeDataModel $item) {
        return items.indexOf($item);
    }

    @Override public void remove(int $index) {
        items.remove($index);
    }

    @Override public void remove(ProductSizeDataModel $item) {
        remove($item);
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
