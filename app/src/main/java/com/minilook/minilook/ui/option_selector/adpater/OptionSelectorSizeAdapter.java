package com.minilook.minilook.ui.option_selector.adpater;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.minilook.minilook.data.model.product.ProductStockDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.option_selector.viewholder.OptionSelectorSizeVH;
import java.util.ArrayList;
import java.util.List;
import lombok.Setter;

public class OptionSelectorSizeAdapter extends RecyclerView.Adapter<OptionSelectorSizeVH>
    implements BaseAdapterDataModel<ProductStockDataModel>, BaseAdapterDataView<ProductStockDataModel> {

    private List<ProductStockDataModel> items = new ArrayList<>();
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

    @Override public void add(ProductStockDataModel $item) {
        items.add($item);
    }

    @Override public void add(int $index, ProductStockDataModel $item) {
        items.add($index, $item);
    }

    @Override public void addAll(List<ProductStockDataModel> $items) {
        items.addAll($items);
    }

    @Override public void set(int $index, ProductStockDataModel $item) {
        items.set($index, $item);
    }

    @Override public void set(List<ProductStockDataModel> $items) {
        items.clear();
        items.addAll($items);
    }

    @Override public ProductStockDataModel get(int $index) {
        return items.get($index);
    }

    @Override public List<ProductStockDataModel> get() {
        return items;
    }

    @Override public int get(ProductStockDataModel $item) {
        return items.indexOf($item);
    }

    @Override public void remove(int $index) {
        items.remove($index);
    }

    @Override public void remove(ProductStockDataModel $item) {
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
