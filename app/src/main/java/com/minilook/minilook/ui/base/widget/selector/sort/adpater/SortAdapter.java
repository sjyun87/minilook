package com.minilook.minilook.ui.base.widget.selector.sort.adpater;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.minilook.minilook.data.model.common.CodeDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.base.widget.selector.sort.SortSelector;
import com.minilook.minilook.ui.base.widget.selector.sort.viewholder.SortItemVH;
import java.util.ArrayList;
import java.util.List;
import lombok.Setter;

public class SortAdapter extends RecyclerView.Adapter<SortItemVH>
    implements BaseAdapterDataModel<CodeDataModel>, BaseAdapterDataView<CodeDataModel> {

    private final List<CodeDataModel> items = new ArrayList<>();
    @Setter private SortSelector.OnSortSelectListener onSortSelectListener;

    @NonNull @Override public SortItemVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SortItemVH(parent);
    }

    @Override public void onBindViewHolder(@NonNull SortItemVH holder, int position) {
        holder.bind(items.get(position));
        holder.setOnSortSelectListener(onSortSelectListener);
    }

    @Override public int getItemCount() {
        return getSize();
    }

    @Override public void add(CodeDataModel $item) {
        items.add($item);
    }

    @Override public void add(int $index, CodeDataModel $item) {
        items.add($index, $item);
    }

    @Override public void addAll(List<CodeDataModel> $items) {
        items.addAll($items);
    }

    @Override public void set(int $index, CodeDataModel $item) {
        items.set($index, $item);
    }

    @Override public void set(List<CodeDataModel> $items) {
        items.clear();
        items.addAll($items);
    }

    @Override public CodeDataModel get(int $index) {
        return items.get($index);
    }

    @Override public List<CodeDataModel> get() {
        return items;
    }

    @Override public int get(CodeDataModel $item) {
        return items.indexOf($item);
    }

    @Override public void remove(int $index) {
        items.remove($index);
    }

    @Override public void remove(CodeDataModel $item) {
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
