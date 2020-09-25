package com.minilook.minilook.ui.point.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.minilook.minilook.data.model.member.PointHistoryDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.point.viewholder.PointHistoryItemVH;
import java.util.ArrayList;
import java.util.List;

public class PointHistoryAdapter extends RecyclerView.Adapter<PointHistoryItemVH>
    implements BaseAdapterDataModel<PointHistoryDataModel>, BaseAdapterDataView<PointHistoryDataModel> {

    private List<PointHistoryDataModel> items = new ArrayList<>();

    @NonNull @Override public PointHistoryItemVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PointHistoryItemVH(parent);
    }

    @Override public void onBindViewHolder(@NonNull PointHistoryItemVH holder, int position) {
        holder.bind(items.get(position));
    }

    @Override public int getItemCount() {
        return getSize();
    }

    @Override public void add(PointHistoryDataModel $item) {
        items.add($item);
    }

    @Override public void add(int $index, PointHistoryDataModel $item) {
        items.add($index, $item);
    }

    @Override public void addAll(List<PointHistoryDataModel> $items) {
        items.addAll($items);
    }

    @Override public void set(int $index, PointHistoryDataModel $item) {
        items.set($index, $item);
    }

    @Override public void set(List<PointHistoryDataModel> $items) {
        items.clear();
        items.addAll($items);
    }

    @Override public PointHistoryDataModel get(int $index) {
        return items.get($index);
    }

    @Override public List<PointHistoryDataModel> get() {
        return items;
    }

    @Override public int get(PointHistoryDataModel $item) {
        return items.indexOf($item);
    }

    @Override public void remove(int $index) {
        items.remove($index);
    }

    @Override public void remove(PointHistoryDataModel $item) {
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
