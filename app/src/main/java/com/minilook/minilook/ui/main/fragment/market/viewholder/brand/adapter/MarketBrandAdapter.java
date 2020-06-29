package com.minilook.minilook.ui.main.fragment.market.viewholder.brand.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minilook.minilook.data.model.brand.BrandInfoDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.main.fragment.market.viewholder.brand.MarketBrandItemVH;

import java.util.ArrayList;
import java.util.List;

public class MarketBrandAdapter extends RecyclerView.Adapter<MarketBrandItemVH> implements
    BaseAdapterDataModel<BrandInfoDataModel>, BaseAdapterDataView<BrandInfoDataModel> {

    private List<BrandInfoDataModel> items = new ArrayList<>();

    @NonNull @Override public MarketBrandItemVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MarketBrandItemVH(parent);
    }

    @Override public void onBindViewHolder(@NonNull MarketBrandItemVH holder, int position) {
        holder.bind(items.get(position));
    }

    @Override public int getItemCount() {
        return getSize();
    }

    @Override public void add(BrandInfoDataModel $item) {
        this.items.add($item);
    }

    @Override public void add(int $index, BrandInfoDataModel $item) {
        this.items.add($index, $item);
    }

    @Override public void addAll(List<BrandInfoDataModel> $items) {
        this.items.addAll($items);
    }

    @Override public void set(int $index, BrandInfoDataModel $item) {
        this.items.set($index, $item);
    }

    @Override public void set(List<BrandInfoDataModel> $items) {
        this.items.clear();
        this.items.addAll($items);
    }

    @Override public BrandInfoDataModel get(int $index) {
        return this.items.get($index);
    }

    @Override public List<BrandInfoDataModel> get() {
        return this.items;
    }

    @Override public void remove(int $index) {
        this.items.remove($index);
    }

    @Override public void remove(BrandInfoDataModel $item) {
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
