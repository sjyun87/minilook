package com.minilook.minilook.ui.scrapbook.view.brand.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.minilook.minilook.data.model.brand.BrandDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.scrapbook.view.brand.viewholder.ScrapbookBrandItemVH;
import java.util.ArrayList;
import java.util.List;

public class ScrapbookBrandAdapter extends RecyclerView.Adapter<ScrapbookBrandItemVH> implements
    BaseAdapterDataModel<BrandDataModel>, BaseAdapterDataView<BrandDataModel> {

    private List<BrandDataModel> items = new ArrayList<>();

    @NonNull @Override
    public ScrapbookBrandItemVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ScrapbookBrandItemVH(parent);
    }

    @Override public void onBindViewHolder(@NonNull ScrapbookBrandItemVH holder, int position) {
        holder.bind(items.get(position));
    }

    @Override public int getItemCount() {
        return getSize();
    }

    @Override public void add(BrandDataModel $item) {
        this.items.add($item);
    }

    @Override public void add(int $index, BrandDataModel $item) {
        this.items.add($index, $item);
    }

    @Override public void addAll(List<BrandDataModel> $items) {
        this.items.addAll($items);
    }

    @Override public void set(int $index, BrandDataModel $item) {
        this.items.set($index, $item);
    }

    @Override public void set(List<BrandDataModel> $items) {
        this.items.clear();
        this.items.addAll($items);
    }

    @Override public BrandDataModel get(int $index) {
        return this.items.get($index);
    }

    @Override public List<BrandDataModel> get() {
        return this.items;
    }

    @Override public void remove(int $index) {
        this.items.remove($index);
    }

    @Override public void remove(BrandDataModel $item) {
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
