package com.minilook.minilook.ui.point.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.minilook.minilook.data.model.user.PointHistoryDataModel;
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
        this.items.add($item);
    }

    @Override public void add(int $index, PointHistoryDataModel $item) {
        this.items.add($index, $item);
    }

    @Override public void addAll(List<PointHistoryDataModel> $items) {
        this.items.addAll($items);
    }

    @Override public void set(int $index, PointHistoryDataModel $item) {
        this.items.set($index, $item);
    }

    @Override public void set(List<PointHistoryDataModel> $items) {
        this.items.clear();
        this.items.addAll($items);
    }

    @Override public PointHistoryDataModel get(int $index) {
        return this.items.get($index);
    }

    @Override public List<PointHistoryDataModel> get() {
        return this.items;
    }

    @Override public int get(PointHistoryDataModel $item) {
        return items.indexOf($item);
    }

    @Override public void remove(int $index) {
        this.items.remove($index);
    }

    @Override public void remove(PointHistoryDataModel $item) {
        this.items.remove($item);
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
