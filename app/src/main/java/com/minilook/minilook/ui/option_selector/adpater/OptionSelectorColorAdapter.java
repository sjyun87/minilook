package com.minilook.minilook.ui.option_selector.adpater;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.minilook.minilook.data.model.product.ProductOptionDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.option_selector.viewholder.OptionSelectorColorVH;
import java.util.ArrayList;
import java.util.List;
import lombok.Setter;

public class OptionSelectorColorAdapter extends RecyclerView.Adapter<OptionSelectorColorVH>
    implements BaseAdapterDataModel<ProductOptionDataModel>, BaseAdapterDataView<ProductOptionDataModel> {

    private List<ProductOptionDataModel> items = new ArrayList<>();
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

    @Override public void add(ProductOptionDataModel $item) {
        items.add($item);
    }

    @Override public void add(int $index, ProductOptionDataModel $item) {
        items.add($index, $item);
    }

    @Override public void addAll(List<ProductOptionDataModel> $items) {
        items.addAll($items);
    }

    @Override public void set(int $index, ProductOptionDataModel $item) {
        items.set($index, $item);
    }

    @Override public void set(List<ProductOptionDataModel> $items) {
        items.clear();
        items.addAll($items);
    }

    @Override public ProductOptionDataModel get(int $index) {
        return items.get($index);
    }

    @Override public List<ProductOptionDataModel> get() {
        return items;
    }

    @Override public int get(ProductOptionDataModel $item) {
        return items.indexOf($item);
    }

    @Override public void remove(int $index) {
        items.remove($index);
    }

    @Override public void remove(ProductOptionDataModel $item) {
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
