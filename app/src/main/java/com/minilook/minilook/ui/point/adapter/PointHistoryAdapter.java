package com.minilook.minilook.ui.point.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.minilook.minilook.data.model.member.PointDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.point.viewholder.PointHistoryItemVH;
import java.util.ArrayList;
import java.util.List;

public class PointHistoryAdapter extends RecyclerView.Adapter<PointHistoryItemVH>
    implements BaseAdapterDataModel<PointDataModel>, BaseAdapterDataView<PointDataModel> {

    private List<PointDataModel> items = new ArrayList<>();

    @NonNull @Override public PointHistoryItemVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PointHistoryItemVH(parent);
    }

    @Override public void onBindViewHolder(@NonNull PointHistoryItemVH holder, int position) {
        holder.bind(items.get(position));
    }

    @Override public int getItemCount() {
        return getSize();
    }

    @Override public void add(PointDataModel $item) {
        items.add($item);
    }

    @Override public void add(int $index, PointDataModel $item) {
        items.add($index, $item);
    }

    @Override public void addAll(List<PointDataModel> $items) {
        items.addAll($items);
    }

    @Override public void set(int $index, PointDataModel $item) {
        items.set($index, $item);
    }

    @Override public void set(List<PointDataModel> $items) {
        items.clear();
        items.addAll($items);
    }

    @Override public PointDataModel get(int $index) {
        return items.get($index);
    }

    @Override public List<PointDataModel> get() {
        return items;
    }

    @Override public int get(PointDataModel $item) {
        return items.indexOf($item);
    }

    @Override public void remove(int $index) {
        items.remove($index);
    }

    @Override public void remove(PointDataModel $item) {
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
