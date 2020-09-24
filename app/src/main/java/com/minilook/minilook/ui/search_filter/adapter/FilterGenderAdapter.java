package com.minilook.minilook.ui.search_filter.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.minilook.minilook.data.model.common.GenderDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.search_filter.viewholder.FilterGenderVH;
import java.util.ArrayList;
import java.util.List;
import lombok.Setter;

public class FilterGenderAdapter extends RecyclerView.Adapter<FilterGenderVH>
    implements BaseAdapterDataModel<GenderDataModel>, BaseAdapterDataView<GenderDataModel> {

    private List<GenderDataModel> items = new ArrayList<>();
    @Setter private FilterGenderVH.OnGenderListener listener;

    @NonNull @Override public FilterGenderVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FilterGenderVH(parent);
    }

    @Override public void onBindViewHolder(@NonNull FilterGenderVH holder, int position) {
        holder.bind(items.get(position));
        holder.setListener(listener);
    }

    @Override public int getItemCount() {
        return getSize();
    }

    @Override public void add(GenderDataModel $item) {
        items.add($item);
    }

    @Override public void add(int $index, GenderDataModel $item) {
        items.add($index, $item);
    }

    @Override public void addAll(List<GenderDataModel> $items) {
        items.addAll($items);
    }

    @Override public void set(int $index, GenderDataModel $item) {
        items.set($index, $item);
    }

    @Override public void set(List<GenderDataModel> $items) {
        items.clear();
        items.addAll($items);
    }

    @Override public GenderDataModel get(int $index) {
        return items.get($index);
    }

    @Override public List<GenderDataModel> get() {
        return items;
    }

    @Override public int get(GenderDataModel $item) {
        return items.indexOf($item);
    }

    @Override public void remove(int $index) {
        items.remove($index);
    }

    @Override public void remove(GenderDataModel $item) {
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
