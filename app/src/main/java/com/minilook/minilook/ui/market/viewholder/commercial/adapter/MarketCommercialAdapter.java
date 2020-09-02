package com.minilook.minilook.ui.market.viewholder.commercial.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minilook.minilook.data.model.commercial.CommercialDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.market.viewholder.commercial.viewholder.MarketCommercialItemVH;

import java.util.ArrayList;
import java.util.List;

public class MarketCommercialAdapter extends RecyclerView.Adapter<MarketCommercialItemVH> implements
    BaseAdapterDataModel<CommercialDataModel>, BaseAdapterDataView<CommercialDataModel> {

    private List<CommercialDataModel> items = new ArrayList<>();

    @NonNull @Override public MarketCommercialItemVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MarketCommercialItemVH(parent);
    }

    @Override public void onBindViewHolder(@NonNull MarketCommercialItemVH holder, int position) {
        holder.bind(items.get(position));
    }

    @Override public int getItemCount() {
        return getSize();
    }

    @Override public void add(CommercialDataModel $item) {
        this.items.add($item);
    }

    @Override public void add(int $index, CommercialDataModel $item) {
        this.items.add($index, $item);
    }

    @Override public void addAll(List<CommercialDataModel> $items) {
        this.items.addAll($items);
    }

    @Override public void set(int $index, CommercialDataModel $item) {
        this.items.set($index, $item);
    }

    @Override public void set(List<CommercialDataModel> $items) {
        this.items.clear();
        this.items.addAll($items);
    }

    @Override public CommercialDataModel get(int $index) {
        return this.items.get($index);
    }

    @Override public List<CommercialDataModel> get() {
        return this.items;
    }

    @Override public void remove(int $index) {
        this.items.remove($index);
    }

    @Override public void remove(CommercialDataModel $item) {
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
