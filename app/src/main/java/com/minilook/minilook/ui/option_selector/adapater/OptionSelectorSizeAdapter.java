package com.minilook.minilook.ui.option_selector.adapater;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.minilook.minilook.data.model.product.OptionSizeDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.option_selector.viewholder.OptionSelectorSizeVH;
import java.util.ArrayList;
import java.util.List;
import lombok.Setter;

public class OptionSelectorSizeAdapter extends RecyclerView.Adapter<OptionSelectorSizeVH>
    implements BaseAdapterDataModel<OptionSizeDataModel>, BaseAdapterDataView<OptionSizeDataModel> {

    private List<OptionSizeDataModel> items = new ArrayList<>();
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

    @Override public void add(OptionSizeDataModel $item) {
        items.add($item);
    }

    @Override public void add(int $index, OptionSizeDataModel $item) {
        items.add($index, $item);
    }

    @Override public void addAll(List<OptionSizeDataModel> $items) {
        items.addAll($items);
    }

    @Override public void set(int $index, OptionSizeDataModel $item) {
        items.set($index, $item);
    }

    @Override public void set(List<OptionSizeDataModel> $items) {
        items.clear();
        items.addAll($items);
    }

    @Override public OptionSizeDataModel get(int $index) {
        return items.get($index);
    }

    @Override public List<OptionSizeDataModel> get() {
        return items;
    }

    @Override public int get(OptionSizeDataModel $item) {
        return items.indexOf($item);
    }

    @Override public void remove(int $index) {
        items.remove($index);
    }

    @Override public void remove(OptionSizeDataModel $item) {
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
        notifyItemRangeInserted($start, $row);
    }
}
