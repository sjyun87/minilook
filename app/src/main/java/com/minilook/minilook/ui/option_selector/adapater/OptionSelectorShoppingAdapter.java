package com.minilook.minilook.ui.option_selector.adapater;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.minilook.minilook.data.model.shopping.ShoppingOptionDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.option_selector.viewholder.OptionSelectorShoppingVH;
import java.util.ArrayList;
import java.util.List;
import lombok.Setter;

public class OptionSelectorShoppingAdapter extends RecyclerView.Adapter<OptionSelectorShoppingVH>
    implements BaseAdapterDataModel<ShoppingOptionDataModel>, BaseAdapterDataView<ShoppingOptionDataModel> {

    private List<ShoppingOptionDataModel> items = new ArrayList<>();
    @Setter private OptionSelectorShoppingVH.OnButtonClickListener OnButtonClickListener;

    @NonNull @Override public OptionSelectorShoppingVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OptionSelectorShoppingVH(parent);
    }

    @Override public void onBindViewHolder(@NonNull OptionSelectorShoppingVH holder, int position) {
        holder.bind(items.get(position));
        holder.setOnButtonClickListener(OnButtonClickListener);
    }

    @Override public int getItemCount() {
        return getSize();
    }

    @Override public void add(ShoppingOptionDataModel $item) {
        items.add($item);
    }

    @Override public void add(int $index, ShoppingOptionDataModel $item) {
        items.add($index, $item);
    }

    @Override public void addAll(List<ShoppingOptionDataModel> $items) {
        items.addAll($items);
    }

    @Override public void set(int $index, ShoppingOptionDataModel $item) {
        items.set($index, $item);
    }

    @Override public void set(List<ShoppingOptionDataModel> $items) {
        items.clear();
        items.addAll($items);
    }

    @Override public ShoppingOptionDataModel get(int $index) {
        return items.get($index);
    }

    @Override public List<ShoppingOptionDataModel> get() {
        return items;
    }

    @Override public int get(ShoppingOptionDataModel $item) {
        return items.indexOf($item);
    }

    @Override public void remove(int $index) {
        items.remove($index);
    }

    @Override public void remove(ShoppingOptionDataModel $item) {
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
