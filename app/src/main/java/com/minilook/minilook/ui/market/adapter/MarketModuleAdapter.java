package com.minilook.minilook.ui.market.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.minilook.minilook.data.code.MarketModuleType;
import com.minilook.minilook.data.model.market.MarketDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.market.viewholder.banner.MarketBannerVH;
import com.minilook.minilook.ui.market.viewholder.brand.MarketBrandVH;
import com.minilook.minilook.ui.market.viewholder.category.MarketCategoryVH;
import com.minilook.minilook.ui.market.viewholder.commercial.MarketCommercialVH;
import com.minilook.minilook.ui.market.viewholder.day.MarketDayVH;
import com.minilook.minilook.ui.market.viewholder.new_arrivals.MarketNewArrivalsVH;
import com.minilook.minilook.ui.market.viewholder.preorder.MarketPreorderVH;
import com.minilook.minilook.ui.market.viewholder.recommend.MarketRecommendVH;
import com.minilook.minilook.ui.market.viewholder.theme.MarketThemeVH;
import com.minilook.minilook.ui.market.viewholder.trend.MarketTrendVH;
import java.util.ArrayList;
import java.util.List;

public class MarketModuleAdapter extends RecyclerView.Adapter<BaseViewHolder<MarketDataModel>> implements
    BaseAdapterDataModel<MarketDataModel>, BaseAdapterDataView<MarketDataModel> {

    private final List<MarketDataModel> items = new ArrayList<>();

    @NonNull @Override
    public BaseViewHolder<MarketDataModel> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MarketModuleType.TYPE_COMMERCIAL.getType()) {
            return new MarketCommercialVH(parent);
        } else if (viewType == MarketModuleType.TYPE_CATEGORY.getType()) {
            return new MarketCategoryVH(parent);
        } else if (viewType == MarketModuleType.TYPE_RECOMMEND.getType()) {
            return new MarketRecommendVH(parent);
        } else if (viewType == MarketModuleType.TYPE_PREORDER.getType()) {
            return new MarketPreorderVH(parent);
        } else if (viewType == MarketModuleType.TYPE_TREND.getType()) {
            return new MarketTrendVH(parent);
        } else if (viewType == MarketModuleType.TYPE_NEW_ARRIVAL.getType()) {
            return new MarketNewArrivalsVH(parent);
        } else if (viewType == MarketModuleType.TYPE_BANNER.getType()) {
            return new MarketBannerVH(parent);
        } else if (viewType == MarketModuleType.TYPE_BRAND.getType()) {
            return new MarketBrandVH(parent);
        } else if (viewType == MarketModuleType.TYPE_THEME.getType()) {
            return new MarketThemeVH(parent);
        } else if (viewType == MarketModuleType.TYPE_DAY.getType()) {
            return new MarketDayVH(parent);
        } else {
            throw new IllegalStateException("Market module type is not matching = " + viewType);
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
        return MarketModuleType.toModuleType(items.get(position).getType());
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

    @Override public void onViewAttachedToWindow(@NonNull BaseViewHolder<MarketDataModel> holder) {
        holder.onAttach();
    }

    @Override public void onViewDetachedFromWindow(@NonNull BaseViewHolder<MarketDataModel> holder) {
        holder.onDetach();
    }
}
