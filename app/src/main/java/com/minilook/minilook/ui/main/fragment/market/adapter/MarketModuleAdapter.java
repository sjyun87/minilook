package com.minilook.minilook.ui.main.fragment.market.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minilook.minilook.data.model.market.MarketModuleDataModel;
import com.minilook.minilook.data.type.MarketModuleType;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.main.fragment.market.viewholder.brand.MarketBrandVH;
import com.minilook.minilook.ui.main.fragment.market.viewholder.promotion.MarketPromotionVH;
import com.minilook.minilook.ui.main.fragment.market.viewholder.recommend.MarketRecommendVH;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class MarketModuleAdapter extends RecyclerView.Adapter<BaseViewHolder> implements
    BaseAdapterDataModel<MarketModuleDataModel>, BaseAdapterDataView<MarketModuleDataModel> {

    private List<MarketModuleDataModel> items = new ArrayList<>();

    @NonNull @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MarketModuleType.TYPE_PROMOTION.getValue()) {
            return new MarketPromotionVH(parent);
        } else if (viewType == MarketModuleType.TYPE_RECOMMEND.getValue()) {
            return new MarketRecommendVH(parent);
        } else if (viewType == MarketModuleType.TYPE_BRAND.getValue()) {
            return new MarketBrandVH(parent);
        } else {
            Timber.e("Type is null -");
            return new BaseViewHolder(parent);
        }
    }

    @SuppressWarnings("unchecked")
    @Override public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override public int getItemCount() {
        return getSize();
    }

    @Override public int getItemViewType(int position) {
        return items.get(position).getModule_type();
    }

    @Override public void add(MarketModuleDataModel $item) {
        this.items.add($item);
    }

    @Override public void add(int $index, MarketModuleDataModel $item) {
        this.items.add($index, $item);
    }

    @Override public void addAll(List<MarketModuleDataModel> $items) {
        this.items.addAll($items);
    }

    @Override public void set(int $index, MarketModuleDataModel $item) {
        this.items.set($index, $item);
    }

    @Override public void set(List<MarketModuleDataModel> $items) {
        this.items.clear();
        this.items.addAll($items);
    }

    @Override public MarketModuleDataModel get(int $index) {
        return this.items.get($index);
    }

    @Override public List<MarketModuleDataModel> get() {
        return this.items;
    }

    @Override public void remove(int $index) {
        this.items.remove($index);
    }

    @Override public void remove(MarketModuleDataModel $item) {
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
