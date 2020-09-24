package com.minilook.minilook.ui.search_filter.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.minilook.minilook.data.model.common.ColorDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.search_filter.viewholder.FilterColorVH;
import java.util.ArrayList;
import java.util.List;
import lombok.Setter;

public class FilterColorAdapter extends RecyclerView.Adapter<FilterColorVH>
    implements BaseAdapterDataModel<ColorDataModel>, BaseAdapterDataView<ColorDataModel> {

    private List<ColorDataModel> items = new ArrayList<>();
    @Setter private FilterColorVH.OnColorListener listener;

    @NonNull @Override public FilterColorVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FilterColorVH(parent);
    }

    @Override public void onBindViewHolder(@NonNull FilterColorVH holder, int position) {
        holder.bind(items.get(position));
        holder.setListener(listener);
    }

    @Override public int getItemCount() {
        return getSize();
    }

    @Override public void add(ColorDataModel $item) {
        items.add($item);
    }

    @Override public void add(int $index, ColorDataModel $item) {
        items.add($index, $item);
    }

    @Override public void addAll(List<ColorDataModel> $items) {
        items.addAll($items);
    }

    @Override public void set(int $index, ColorDataModel $item) {
        items.set($index, $item);
    }

    @Override public void set(List<ColorDataModel> $items) {
        items.clear();
        items.addAll($items);
    }

    @Override public ColorDataModel get(int $index) {
        return items.get($index);
    }

    @Override public List<ColorDataModel> get() {
        return items;
    }

    @Override public int get(ColorDataModel $item) {
        return items.indexOf($item);
    }

    @Override public void remove(int $index) {
        items.remove($index);
    }

    @Override public void remove(ColorDataModel $item) {
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
