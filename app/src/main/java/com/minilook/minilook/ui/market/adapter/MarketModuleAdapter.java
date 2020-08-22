package com.minilook.minilook.ui.market.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.minilook.minilook.data.model.market.MarketDataModel;
import com.minilook.minilook.data.type.MarketModuleType;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.market.viewholder.brand.MarketBrandVH;
import com.minilook.minilook.ui.market.viewholder.commercial.MarketCommercialVH;
import com.minilook.minilook.ui.market.viewholder.filter.MarketFilterVH;
import com.minilook.minilook.ui.market.viewholder.limited.MarketLimitedVH;
import com.minilook.minilook.ui.market.viewholder.new_arrivals.MarketNewArrivalsVH;
import com.minilook.minilook.ui.market.viewholder.recommend.MarketRecommendVH;
import java.util.ArrayList;
import java.util.List;
import timber.log.Timber;

public class MarketModuleAdapter extends RecyclerView.Adapter<BaseViewHolder<MarketDataModel>> implements
    BaseAdapterDataModel<MarketDataModel>, BaseAdapterDataView<MarketDataModel> {

    private List<MarketDataModel> items = new ArrayList<>();

    @NonNull @Override
    public BaseViewHolder<MarketDataModel> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MarketModuleType.TYPE_COMMERCIAL.getValue()) {
            return new MarketCommercialVH(parent);
        } else if (viewType == MarketModuleType.TYPE_LIMITED.getValue()) {
            return new MarketLimitedVH(parent);
        } else if (viewType == MarketModuleType.TYPE_RECOMMEND_4.getValue()) {
            return new MarketRecommendVH(parent, 4);
        } else if (viewType == MarketModuleType.TYPE_RECOMMEND_5.getValue()) {
            return new MarketRecommendVH(parent, 5);
        } else if (viewType == MarketModuleType.TYPE_RECOMMEND_6.getValue()) {
            return new MarketRecommendVH(parent, 6);
        } else if (viewType == MarketModuleType.TYPE_RECOMMEND_9.getValue()) {
            return new MarketRecommendVH(parent, 9);
        } else if (viewType == MarketModuleType.TYPE_NEW_ARRIVALS.getValue()) {
            return new MarketNewArrivalsVH(parent);
        } else if (viewType == MarketModuleType.TYPE_BRAND.getValue()) {
            return new MarketBrandVH(parent);
        } else if (viewType == MarketModuleType.TYPE_FILTER.getValue()) {
            return new MarketFilterVH(parent);
        } else {
            Timber.e("Market Module type is null..");
            return new BaseViewHolder<>(parent);
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
        return items.get(position).getType();
    }

    @Override public void add(MarketDataModel $item) {
        this.items.add($item);
    }

    @Override public void add(int $index, MarketDataModel $item) {
        this.items.add($index, $item);
    }

    @Override public void addAll(List<MarketDataModel> $items) {
        this.items.addAll($items);
    }

    @Override public void set(int $index, MarketDataModel $item) {
        this.items.set($index, $item);
    }

    @Override public void set(List<MarketDataModel> $items) {
        this.items.clear();
        this.items.addAll($items);
    }

    @Override public MarketDataModel get(int $index) {
        return this.items.get($index);
    }

    @Override public List<MarketDataModel> get() {
        return this.items;
    }

    @Override public void remove(int $index) {
        this.items.remove($index);
    }

    @Override public void remove(MarketDataModel $item) {
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
