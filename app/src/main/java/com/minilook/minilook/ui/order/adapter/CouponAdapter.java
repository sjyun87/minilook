package com.minilook.minilook.ui.order.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.minilook.minilook.data.model.user.CouponDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.order.viewholder.CouponItemVH;
import java.util.ArrayList;
import java.util.List;

public class CouponAdapter extends RecyclerView.Adapter<CouponItemVH>
    implements BaseAdapterDataModel<CouponDataModel>, BaseAdapterDataView<CouponDataModel> {

    private List<CouponDataModel> items = new ArrayList<>();

    @NonNull @Override public CouponItemVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CouponItemVH(parent);
    }

    @Override public void onBindViewHolder(@NonNull CouponItemVH holder, int position) {
        holder.bind(items.get(position));
    }

    @Override public int getItemCount() {
        return getSize();
    }

    @Override public void add(CouponDataModel $item) {
        this.items.add($item);
    }

    @Override public void add(int $index, CouponDataModel $item) {
        this.items.add($index, $item);
    }

    @Override public void addAll(List<CouponDataModel> $items) {
        this.items.addAll($items);
    }

    @Override public void set(int $index, CouponDataModel $item) {
        this.items.set($index, $item);
    }

    @Override public void set(List<CouponDataModel> $items) {
        this.items.clear();
        this.items.addAll($items);
    }

    @Override public CouponDataModel get(int $index) {
        return this.items.get($index);
    }

    @Override public List<CouponDataModel> get() {
        return this.items;
    }

    @Override public int get(CouponDataModel $item) {
        return items.indexOf($item);
    }

    @Override public void remove(int $index) {
        this.items.remove($index);
    }

    @Override public void remove(CouponDataModel $item) {
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