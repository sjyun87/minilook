package com.minilook.minilook.ui.option_selector.adapater;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.minilook.minilook.data.model.product.OptionProductDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.option_selector.viewholder.OptionSelectorProductVH;
import java.util.ArrayList;
import java.util.List;
import lombok.Setter;

public class OptionSelectorProductAdapter extends RecyclerView.Adapter<OptionSelectorProductVH>
    implements BaseAdapterDataModel<OptionProductDataModel>, BaseAdapterDataView<OptionProductDataModel> {

    private List<OptionProductDataModel> items = new ArrayList<>();
    @Setter private OptionSelectorProductVH.OnProductSelectedListener onProductSelectedListener;

    @NonNull @Override public OptionSelectorProductVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OptionSelectorProductVH(parent);
    }

    @Override public void onBindViewHolder(@NonNull OptionSelectorProductVH holder, int position) {
        holder.bind(items.get(position), position);
        holder.setOnProductSelectedListener(onProductSelectedListener);
    }

    @Override public int getItemCount() {
        return getSize();
    }

    @Override public void add(OptionProductDataModel $item) {
        items.add($item);
    }

    @Override public void add(int $index, OptionProductDataModel $item) {
        items.add($index, $item);
    }

    @Override public void addAll(List<OptionProductDataModel> $items) {
        items.addAll($items);
    }

    @Override public void set(int $index, OptionProductDataModel $item) {
        items.set($index, $item);
    }

    @Override public void set(List<OptionProductDataModel> $items) {
        items.clear();
        items.addAll($items);
    }

    @Override public OptionProductDataModel get(int $index) {
        return items.get($index);
    }

    @Override public List<OptionProductDataModel> get() {
        return items;
    }

    @Override public int get(OptionProductDataModel $item) {
        return items.indexOf($item);
    }

    @Override public void remove(int $index) {
        items.remove($index);
    }

    @Override public void remove(OptionProductDataModel $item) {
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
