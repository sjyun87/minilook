package com.minilook.minilook.ui.base.widget.picker.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.base.widget.picker.viewholder.SnapPickerItemVH;
import java.util.ArrayList;
import java.util.List;

public class SnapPickerAdapter extends RecyclerView.Adapter<SnapPickerItemVH>
    implements BaseAdapterDataModel<Integer>, BaseAdapterDataView<Integer> {

    private static final int TYPE_FIRST = 0;
    private static final int TYPE_END = 1;
    private static final int TYPE_MIDDLE = 2;

    private final List<Integer> items = new ArrayList<>();

    @NonNull @Override public SnapPickerItemVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SnapPickerItemVH(parent, viewType == TYPE_FIRST, viewType == TYPE_END);
    }

    @Override public void onBindViewHolder(@NonNull SnapPickerItemVH holder, int position) {
        holder.bind(items.get(position));
    }

    @Override public int getItemCount() {
        return getSize();
    }

    @Override public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_FIRST;
        } else if (position == items.size() - 1) {
            return TYPE_END;
        }
        return TYPE_MIDDLE;
    }

    @Override public void add(Integer $item) {
        items.add($item);
    }

    @Override public void add(int $index, Integer $item) {
        items.add($index, $item);
    }

    @Override public void addAll(List<Integer> $items) {
        items.addAll($items);
    }

    @Override public void set(int $index, Integer $item) {
        items.set($index, $item);
    }

    @Override public void set(List<Integer> $items) {
        items.clear();
        items.addAll($items);
    }

    @Override public Integer get(int $index) {
        return items.get($index);
    }

    @Override public List<Integer> get() {
        return items;
    }

    @Override public int get(Integer $item) {
        return items.indexOf($item);
    }

    @Override public void remove(int $index) {
        items.remove($index);
    }

    @Override public void remove(Integer $item) {
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
