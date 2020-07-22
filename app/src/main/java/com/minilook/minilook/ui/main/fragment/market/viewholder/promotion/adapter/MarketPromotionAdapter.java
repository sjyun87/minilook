package com.minilook.minilook.ui.main.fragment.market.viewholder.promotion.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.minilook.minilook.data.model.market.MarketPromotionDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.main.fragment.market.viewholder.promotion.MarketPromotionItemVH;
import java.util.ArrayList;
import java.util.List;

public class MarketPromotionAdapter extends RecyclerView.Adapter<MarketPromotionItemVH> implements
    BaseAdapterDataModel<MarketPromotionDataModel>, BaseAdapterDataView<MarketPromotionDataModel> {

    private List<MarketPromotionDataModel> items = new ArrayList<>();

    @NonNull @Override public MarketPromotionItemVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MarketPromotionItemVH(parent);
    }

    @Override public void onBindViewHolder(@NonNull MarketPromotionItemVH holder, int position) {
        holder.bind(items.get(position));
    }

    @Override public int getItemCount() {
        return getSize();
    }

    @Override public void add(MarketPromotionDataModel $item) {
        this.items.add($item);
    }

    @Override public void add(int $index, MarketPromotionDataModel $item) {
        this.items.add($index, $item);
    }

    @Override public void addAll(List<MarketPromotionDataModel> $items) {
        this.items.addAll($items);
    }

    @Override public void set(int $index, MarketPromotionDataModel $item) {
        this.items.set($index, $item);
    }

    @Override public void set(List<MarketPromotionDataModel> $items) {
        this.items.clear();
        this.items.addAll($items);
    }

    @Override public MarketPromotionDataModel get(int $index) {
        return this.items.get($index);
    }

    @Override public List<MarketPromotionDataModel> get() {
        return this.items;
    }

    @Override public void remove(int $index) {
        this.items.remove($index);
    }

    @Override public void remove(MarketPromotionDataModel $item) {
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
