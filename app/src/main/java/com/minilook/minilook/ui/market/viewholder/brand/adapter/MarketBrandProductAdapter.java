package com.minilook.minilook.ui.market.viewholder.brand.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.market.viewholder.brand.viewholder.MarketBrandProductVH;
import java.util.ArrayList;
import java.util.List;

public class MarketBrandProductAdapter extends RecyclerView.Adapter<MarketBrandProductVH> implements
    BaseAdapterDataModel<ProductDataModel>, BaseAdapterDataView<ProductDataModel> {

    private final List<ProductDataModel> items = new ArrayList<>();

    @NonNull @Override public MarketBrandProductVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MarketBrandProductVH(parent);
    }

    @Override public void onBindViewHolder(@NonNull MarketBrandProductVH holder, int position) {
        holder.bind(items.get(position));
    }

    @Override public int getItemCount() {
        return getSize();
    }

    @Override public void add(ProductDataModel $item) {
        items.add($item);
    }

    @Override public void add(int $index, ProductDataModel $item) {
        items.add($index, $item);
    }

    @Override public void addAll(List<ProductDataModel> $items) {
        items.addAll($items);
    }

    @Override public void set(int $index, ProductDataModel $item) {
        items.set($index, $item);
    }

    @Override public void set(List<ProductDataModel> $items) {
        items.clear();
        items.addAll($items);
    }

    @Override public ProductDataModel get(int $index) {
        return items.get($index);
    }

    @Override public List<ProductDataModel> get() {
        return items;
    }

    @Override public int get(ProductDataModel $item) {
        return items.indexOf($item);
    }

    @Override public void remove(int $index) {
        items.remove($index);
    }

    @Override public void remove(ProductDataModel $item) {
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
