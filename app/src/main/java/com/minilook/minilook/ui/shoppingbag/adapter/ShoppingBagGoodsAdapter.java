package com.minilook.minilook.ui.shoppingbag.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.minilook.minilook.data.model.product.GoodsDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.shoppingbag.viewholder.ShoppingBagGoodsItemVH;
import java.util.ArrayList;
import java.util.List;

public class ShoppingBagGoodsAdapter extends RecyclerView.Adapter<ShoppingBagGoodsItemVH>
    implements BaseAdapterDataModel<GoodsDataModel>, BaseAdapterDataView<GoodsDataModel> {

    private List<GoodsDataModel> items = new ArrayList<>();

    @NonNull @Override public ShoppingBagGoodsItemVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ShoppingBagGoodsItemVH(parent);
    }

    @Override public void onBindViewHolder(@NonNull ShoppingBagGoodsItemVH holder, int position) {
        holder.bind(items.get(position));
    }

    @Override public int getItemCount() {
        return getSize();
    }

    @Override public void add(GoodsDataModel $item) {
        this.items.add($item);
    }

    @Override public void add(int $index, GoodsDataModel $item) {
        this.items.add($index, $item);
    }

    @Override public void addAll(List<GoodsDataModel> $items) {
        this.items.addAll($items);
    }

    @Override public void set(int $index, GoodsDataModel $item) {
        this.items.set($index, $item);
    }

    @Override public void set(List<GoodsDataModel> $items) {
        this.items.clear();
        this.items.addAll($items);
    }

    @Override public GoodsDataModel get(int $index) {
        return this.items.get($index);
    }

    @Override public List<GoodsDataModel> get() {
        return this.items;
    }

    @Override public int get(GoodsDataModel $item) {
        return items.indexOf($item);
    }

    @Override public void remove(int $index) {
        this.items.remove($index);
    }

    @Override public void remove(GoodsDataModel $item) {
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
