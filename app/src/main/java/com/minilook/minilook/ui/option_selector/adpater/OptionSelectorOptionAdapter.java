package com.minilook.minilook.ui.option_selector.adpater;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.minilook.minilook.data.model.shopping.ShoppingOptionDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.option_selector.viewholder.OptionSelectorOptionVH;
import java.util.ArrayList;
import java.util.List;
import lombok.Setter;

public class OptionSelectorOptionAdapter extends RecyclerView.Adapter<OptionSelectorOptionVH>
    implements BaseAdapterDataModel<ShoppingOptionDataModel>, BaseAdapterDataView<ShoppingOptionDataModel> {

    private List<ShoppingOptionDataModel> items = new ArrayList<>();
    @Setter private OptionSelectorOptionVH.OnButtonClickListener OnButtonClickListener;

    @NonNull @Override public OptionSelectorOptionVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OptionSelectorOptionVH(parent);
    }

    @Override public void onBindViewHolder(@NonNull OptionSelectorOptionVH holder, int position) {
        holder.bind(items.get(position));
        holder.setOnButtonClickListener(OnButtonClickListener);
    }

    @Override public int getItemCount() {
        return getSize();
    }

    @Override public void add(ShoppingOptionDataModel $item) {
        this.items.add($item);
    }

    @Override public void add(int $index, ShoppingOptionDataModel $item) {
        this.items.add($index, $item);
    }

    @Override public void addAll(List<ShoppingOptionDataModel> $items) {
        this.items.addAll($items);
    }

    @Override public void set(int $index, ShoppingOptionDataModel $item) {
        this.items.set($index, $item);
    }

    @Override public void set(List<ShoppingOptionDataModel> $items) {
        this.items.clear();
        this.items.addAll($items);
    }

    @Override public ShoppingOptionDataModel get(int $index) {
        return this.items.get($index);
    }

    @Override public List<ShoppingOptionDataModel> get() {
        return this.items;
    }

    @Override public int get(ShoppingOptionDataModel $item) {
        return items.indexOf($item);
    }

    @Override public void remove(int $index) {
        this.items.remove($index);
    }

    @Override public void remove(ShoppingOptionDataModel $item) {
        items.remove($item);
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
