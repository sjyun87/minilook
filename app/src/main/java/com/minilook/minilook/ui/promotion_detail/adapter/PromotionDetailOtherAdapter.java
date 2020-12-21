package com.minilook.minilook.ui.promotion_detail.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.minilook.minilook.data.model.promotion.PromotionDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.promotion_detail.viewholder.PromotionDetailOtherVH;
import java.util.ArrayList;
import java.util.List;

public class PromotionDetailOtherAdapter extends RecyclerView.Adapter<PromotionDetailOtherVH>
    implements BaseAdapterDataModel<PromotionDataModel>, BaseAdapterDataView<PromotionDataModel> {

    private final List<PromotionDataModel> items = new ArrayList<>();

    @NonNull @Override public PromotionDetailOtherVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PromotionDetailOtherVH(parent, getSize() == 1);
    }

    @Override public void onBindViewHolder(@NonNull PromotionDetailOtherVH holder, int position) {
        holder.bind(items.get(position));
    }

    @Override public int getItemCount() {
        return getSize();
    }

    @Override public void add(PromotionDataModel $item) {
        items.add($item);
    }

    @Override public void add(int $index, PromotionDataModel $item) {
        items.add($index, $item);
    }

    @Override public void addAll(List<PromotionDataModel> $items) {
        items.addAll($items);
    }

    @Override public void set(int $index, PromotionDataModel $item) {
        items.set($index, $item);
    }

    @Override public void set(List<PromotionDataModel> $items) {
        items.clear();
        items.addAll($items);
    }

    @Override public PromotionDataModel get(int $index) {
        return items.get($index);
    }

    @Override public List<PromotionDataModel> get() {
        return items;
    }

    @Override public int get(PromotionDataModel $item) {
        return items.indexOf($item);
    }

    @Override public void remove(int $index) {
        items.remove($index);
    }

    @Override public void remove(PromotionDataModel $item) {
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
