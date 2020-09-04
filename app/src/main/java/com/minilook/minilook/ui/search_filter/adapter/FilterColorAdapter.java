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
        this.items.add($item);
    }

    @Override public void add(int $index, ColorDataModel $item) {
        this.items.add($index, $item);
    }

    @Override public void addAll(List<ColorDataModel> $items) {
        this.items.addAll($items);
    }

    @Override public void set(int $index, ColorDataModel $item) {
        this.items.set($index, $item);
    }

    @Override public void set(List<ColorDataModel> $items) {
        this.items.clear();
        this.items.addAll($items);
    }

    @Override public ColorDataModel get(int $index) {
        return this.items.get($index);
    }

    @Override public List<ColorDataModel> get() {
        return this.items;
    }

    @Override public int get(ColorDataModel $item) {
        return items.indexOf($item);
    }

    @Override public void remove(int $index) {
        this.items.remove($index);
    }

    @Override public void remove(ColorDataModel $item) {
        this.remove($item);
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
