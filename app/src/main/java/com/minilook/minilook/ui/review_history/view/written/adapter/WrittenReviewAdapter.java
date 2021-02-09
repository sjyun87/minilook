package com.minilook.minilook.ui.review_history.view.written.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.minilook.minilook.data.model.review.ReviewDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.review_history.view.written.viewholder.WrittenReviewItemVH;
import java.util.ArrayList;
import java.util.List;

public class WrittenReviewAdapter extends RecyclerView.Adapter<WrittenReviewItemVH> implements
    BaseAdapterDataModel<ReviewDataModel>, BaseAdapterDataView<ReviewDataModel> {

    private final List<ReviewDataModel> items = new ArrayList<>();

    @NonNull @Override public WrittenReviewItemVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WrittenReviewItemVH(parent);
    }

    @Override public void onBindViewHolder(@NonNull WrittenReviewItemVH holder, int position) {
        holder.bind(items.get(position));
    }

    @Override public int getItemCount() {
        return getSize();
    }

    @Override public void add(ReviewDataModel $item) {
        items.add($item);
    }

    @Override public void add(int $index, ReviewDataModel $item) {
        items.add($index, $item);
    }

    @Override public void addAll(List<ReviewDataModel> $items) {
        items.addAll($items);
    }

    @Override public void set(int $index, ReviewDataModel $item) {
        items.set($index, $item);
    }

    @Override public void set(List<ReviewDataModel> $items) {
        items.clear();
        items.addAll($items);
    }

    @Override public ReviewDataModel get(int $index) {
        return items.get($index);
    }

    @Override public List<ReviewDataModel> get() {
        return items;
    }

    @Override public int get(ReviewDataModel $item) {
        return items.indexOf($item);
    }

    @Override public void remove(int $index) {
        items.remove($index);
    }

    @Override public void remove(ReviewDataModel $item) {
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
