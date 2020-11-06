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
        int realPosition = position % items.size();
        holder.bind(items.get(realPosition), realPosition, items.size());
    }

    public int getRealItemCount() {
        return items.size();
    }

    @Override public int getItemCount() {
        return getSize();
    }

    @Override public void add(CommercialDataModel $item) {
        items.add($item);
    }

    @Override public void add(int $index, CommercialDataModel $item) {
        items.add($index, $item);
    }

    @Override public void addAll(List<CommercialDataModel> $items) {
        items.addAll($items);
    }

    @Override public void set(int $index, CommercialDataModel $item) {
        items.set($index, $item);
    }

    @Override public void set(List<CommercialDataModel> $items) {
        items.clear();
        items.addAll($items);
    }

    @Override public CommercialDataModel get(int $index) {
        return items.get($index);
    }

    @Override public List<CommercialDataModel> get() {
        return items;
    }

    @Override public int get(CommercialDataModel $item) {
        return items.indexOf($item);
    }

    @Override public void remove(int $index) {
        items.remove($index);
    }

    @Override public void remove(CommercialDataModel $item) {
        items.remove($item);
    }

    @Override public void removeAll() {
        items.clear();
    }

    @Override public void clear() {
        items.clear();
    }

    @Override public int getSize() {
        return Integer.MAX_VALUE;
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
