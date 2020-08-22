package com.minilook.minilook.ui.market.viewholder.brand.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.minilook.minilook.data.model.brand.BrandDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.market.viewholder.brand.viewholder.MarketBrandMenuVH;
import java.util.ArrayList;
import java.util.List;
import lombok.Setter;

public class MarketBrandMenuAdapter extends RecyclerView.Adapter<MarketBrandMenuVH> implements
    BaseAdapterDataModel<BrandDataModel>, BaseAdapterDataView<BrandDataModel> {

    private List<BrandDataModel> items = new ArrayList<>();
    @Setter private MarketBrandMenuVH.OnMenuClickListener onMenuClickListener;

    @NonNull @Override
    public MarketBrandMenuVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MarketBrandMenuVH(parent);
    }

    @Override public void onBindViewHolder(@NonNull MarketBrandMenuVH holder, int position) {
        holder.bind(items.get(position));
        holder.setOnMenuClickListener(onMenuClickListener);
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
