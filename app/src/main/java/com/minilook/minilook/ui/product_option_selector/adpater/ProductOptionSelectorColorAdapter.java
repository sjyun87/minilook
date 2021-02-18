package com.minilook.minilook.ui.product_option_selector.adpater;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.minilook.minilook.data.model.product.OptionColorDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.product_option_selector.viewholder.ProductOptionSelectorColorVH;
import java.util.ArrayList;
import java.util.List;
import lombok.Setter;

public class ProductOptionSelectorColorAdapter extends RecyclerView.Adapter<ProductOptionSelectorColorVH>
    implements BaseAdapterDataModel<OptionColorDataModel>, BaseAdapterDataView<OptionColorDataModel> {

    private List<OptionColorDataModel> items = new ArrayList<>();
    @Setter private ProductOptionSelectorColorVH.OnColorSelectedListener onColorSelectedListener;

    @NonNull @Override public ProductOptionSelectorColorVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductOptionSelectorColorVH(parent);
    }

    @Override public void onBindViewHolder(@NonNull ProductOptionSelectorColorVH holder, int position) {
        holder.bind(items.get(position));
        holder.setOnColorSelectedListener(onColorSelectedListener);
    }

    @Override public int getItemCount() {
        return getSize();
    }

    @Override public void add(OptionColorDataModel $item) {
        items.add($item);
    }

    @Override public void add(int $index, OptionColorDataModel $item) {
        items.add($index, $item);
    }

    @Override public void addAll(List<OptionColorDataModel> $items) {
        items.addAll($items);
    }

    @Override public void set(int $index, OptionColorDataModel $item) {
        items.set($index, $item);
    }

    @Override public void set(List<OptionColorDataModel> $items) {
        items.clear();
        items.addAll($items);
    }

    @Override public OptionColorDataModel get(int $index) {
        return items.get($index);
    }

    @Override public List<OptionColorDataModel> get() {
        return items;
    }

    @Override public int get(OptionColorDataModel $item) {
        return items.indexOf($item);
    }

    @Override public void remove(int $index) {
        items.remove($index);
    }

    @Override public void remove(OptionColorDataModel $item) {
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
