package com.minilook.minilook.ui.market.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.minilook.minilook.data.model.market.MarketDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.base.BaseViewHolder;
import java.util.ArrayList;
import java.util.List;

public class MarketModuleAdapter extends RecyclerView.Adapter<BaseViewHolder<MarketDataModel>> implements
    BaseAdapterDataModel<MarketDataModel>, BaseAdapterDataView<MarketDataModel> {

    private List<MarketDataModel> items = new ArrayList<>();

    @NonNull @Override
    public BaseViewHolder<MarketDataModel> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //if (viewType == MarketModuleType.TYPE_COMMERCIAL.getValue()) {
        //    return new MarketCommercialVH(parent);
        //} else if (viewType == MarketModuleType.TYPE_LIMITED.getValue()) {
        //    return new MarketLimitedVH(parent);
        //} else if (viewType == MarketModuleType.TYPE_RECOMMEND_4.getValue()) {
        //    return new MarketRecommendVH(parent, 4);
        //} else if (viewType == MarketModuleType.TYPE_RECOMMEND_5.getValue()) {
        //    return new MarketRecommendVH(parent, 5);
        //} else if (viewType == MarketModuleType.TYPE_RECOMMEND_6.getValue()) {
        //    return new MarketRecommendVH(parent, 6);
        //} else if (viewType == MarketModuleType.TYPE_RECOMMEND_9.getValue()) {
        //    return new MarketRecommendVH(parent, 9);
        //} else if (viewType == MarketModuleType.TYPE_NEW_ARRIVALS.getValue()) {
        //    return new MarketNewArrivalsVH(parent);
        //} else if (viewType == MarketModuleType.TYPE_BRAND.getValue()) {
        //    return new MarketBrandVH(parent);
        //} else if (viewType == MarketModuleType.TYPE_FILTER.getValue()) {
        //    return new MarketFilterVH(parent);
        //} else {
        //    Timber.e("Market Module type is null..");
        //    return new BaseViewHolder<>(parent);
        //}
        return new BaseViewHolder<>(parent);
    }

    @SuppressWarnings("unchecked")
    @Override public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override public int getItemCount() {
        return getSize();
    }

    @Override public int getItemViewType(int position) {
        return items.get(position).getType();
    }

    @Override public void add(MarketDataModel $item) {
        items.add($item);
    }

    @Override public void add(int $index, MarketDataModel $item) {
        items.add($index, $item);
    }

    @Override public void addAll(List<MarketDataModel> $items) {
        items.addAll($items);
    }

    @Override public void set(int $index, MarketDataModel $item) {
        items.set($index, $item);
    }

    @Override public void set(List<MarketDataModel> $items) {
        items.clear();
        items.addAll($items);
    }

    @Override public MarketDataModel get(int $index) {
        return items.get($index);
    }

    @Override public List<MarketDataModel> get() {
        return items;
    }

    @Override public int get(MarketDataModel $item) {
        return items.indexOf($item);
    }

    @Override public void remove(int $index) {
        items.remove($index);
    }

    @Override public void remove(MarketDataModel $item) {
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
