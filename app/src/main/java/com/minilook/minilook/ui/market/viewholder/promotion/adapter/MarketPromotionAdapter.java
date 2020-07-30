package com.minilook.minilook.ui.market.viewholder.promotion.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.minilook.minilook.data.model.promotion.PromotionDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.market.viewholder.promotion.MarketPromotionItemVH;
import java.util.ArrayList;
import java.util.List;

public class MarketPromotionAdapter extends RecyclerView.Adapter<MarketPromotionItemVH> implements
    BaseAdapterDataModel<PromotionDataModel>, BaseAdapterDataView<PromotionDataModel> {

    private List<PromotionDataModel> items = new ArrayList<>();

    @NonNull @Override public MarketPromotionItemVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MarketPromotionItemVH(parent);
    }

    @Override public void onBindViewHolder(@NonNull MarketPromotionItemVH holder, int position) {
        holder.bind(items.get(position));
    }

    @Override public int getItemCount() {
        return getSize();
    }

    @Override public void add(PromotionDataModel $item) {
        this.items.add($item);
    }

    @Override public void add(int $index, PromotionDataModel $item) {
        this.items.add($index, $item);
    }

    @Override public void addAll(List<PromotionDataModel> $items) {
        this.items.addAll($items);
    }

    @Override public void set(int $index, PromotionDataModel $item) {
        this.items.set($index, $item);
    }

    @Override public void set(List<PromotionDataModel> $items) {
        this.items.clear();
        this.items.addAll($items);
    }

    @Override public PromotionDataModel get(int $index) {
        return this.items.get($index);
    }

    @Override public List<PromotionDataModel> get() {
        return this.items;
    }

    @Override public void remove(int $index) {
        this.items.remove($index);
    }

    @Override public void remove(PromotionDataModel $item) {
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
