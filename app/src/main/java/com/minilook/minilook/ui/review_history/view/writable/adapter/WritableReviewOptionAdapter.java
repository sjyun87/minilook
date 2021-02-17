package com.minilook.minilook.ui.review_history.view.writable.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.minilook.minilook.data.model.order.OrderProductDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.review_history.view.writable.viewholder.WritableReviewOptionItemVH;
import java.util.ArrayList;
import java.util.List;
import lombok.Setter;

public class WritableReviewOptionAdapter extends RecyclerView.Adapter<WritableReviewOptionItemVH> implements
    BaseAdapterDataModel<OrderProductDataModel>, BaseAdapterDataView<OrderProductDataModel> {

    private final List<OrderProductDataModel> items = new ArrayList<>();
    @Setter private WritableReviewOptionItemVH.OnReviewWriteClickListener onReviewWriteClickListener;

    @NonNull @Override public WritableReviewOptionItemVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WritableReviewOptionItemVH(parent);
    }

    @Override public void onBindViewHolder(@NonNull WritableReviewOptionItemVH holder, int position) {
        holder.bind(items.get(position));
        if (onReviewWriteClickListener != null) holder.setOnReviewWriteClickListener(onReviewWriteClickListener);
    }

    @Override public int getItemCount() {
        return getSize();
    }

    @Override public void add(OrderProductDataModel $item) {
        items.add($item);
    }

    @Override public void add(int $index, OrderProductDataModel $item) {
        items.add($index, $item);
    }

    @Override public void addAll(List<OrderProductDataModel> $items) {
        items.addAll($items);
    }

    @Override public void set(int $index, OrderProductDataModel $item) {
        items.set($index, $item);
    }

    @Override public void set(List<OrderProductDataModel> $items) {
        items.clear();
        items.addAll($items);
    }

    @Override public OrderProductDataModel get(int $index) {
        return items.get($index);
    }

    @Override public List<OrderProductDataModel> get() {
        return items;
    }

    @Override public int get(OrderProductDataModel $item) {
        return items.indexOf($item);
    }

    @Override public void remove(int $index) {
        items.remove($index);
    }

    @Override public void remove(OrderProductDataModel $item) {
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
