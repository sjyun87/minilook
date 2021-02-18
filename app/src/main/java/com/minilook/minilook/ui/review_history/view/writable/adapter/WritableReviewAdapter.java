package com.minilook.minilook.ui.review_history.view.writable.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.minilook.minilook.data.model.order.OrderHistoryDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.review_history.view.writable.viewholder.WritableReviewItemVH;
import java.util.ArrayList;
import java.util.List;

public class WritableReviewAdapter extends RecyclerView.Adapter<WritableReviewItemVH> implements
    BaseAdapterDataModel<OrderHistoryDataModel>, BaseAdapterDataView<OrderHistoryDataModel> {

    private final List<OrderHistoryDataModel> items = new ArrayList<>();

    @NonNull @Override public WritableReviewItemVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WritableReviewItemVH(parent);
    }

    @Override public void onBindViewHolder(@NonNull WritableReviewItemVH holder, int position) {
        holder.bind(items.get(position));
    }

    @Override public int getItemCount() {
        return getSize();
    }

    @Override public void add(OrderHistoryDataModel $item) {
        items.add($item);
    }

    @Override public void add(int $index, OrderHistoryDataModel $item) {
        items.add($index, $item);
    }

    @Override public void addAll(List<OrderHistoryDataModel> $items) {
        items.addAll($items);
    }

    @Override public void set(int $index, OrderHistoryDataModel $item) {
        items.set($index, $item);
    }

    @Override public void set(List<OrderHistoryDataModel> $items) {
        items.clear();
        items.addAll($items);
    }

    @Override public OrderHistoryDataModel get(int $index) {
        return items.get($index);
    }

    @Override public List<OrderHistoryDataModel> get() {
        return items;
    }

    @Override public int get(OrderHistoryDataModel $item) {
        return items.indexOf($item);
    }

    @Override public void remove(int $index) {
        items.remove($index);
    }

    @Override public void remove(OrderHistoryDataModel $item) {
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
