package com.minilook.minilook.ui.market.viewholder.md_pick.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.minilook.minilook.data.model.category.CategoryDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.market.viewholder.md_pick.viewholder.MarketCategoryItemVH;
import java.util.ArrayList;
import java.util.List;
import lombok.Setter;

public class MarketCategoryAdapter extends RecyclerView.Adapter<MarketCategoryItemVH> implements
    BaseAdapterDataModel<CategoryDataModel>, BaseAdapterDataView<CategoryDataModel> {

    private List<CategoryDataModel> items = new ArrayList<>();
    @Setter private MarketCategoryItemVH.OnItemClickListener onItemClickListener;

    @NonNull @Override public MarketCategoryItemVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MarketCategoryItemVH(parent);
    }

    @Override public void onBindViewHolder(@NonNull MarketCategoryItemVH holder, int position) {
        holder.bind(items.get(position));
        holder.setOnItemClickListener(onItemClickListener);
    }

    @Override public int getItemCount() {
        return getSize();
    }

    @Override public void add(CategoryDataModel $item) {
        this.items.add($item);
    }

    @Override public void add(int $index, CategoryDataModel $item) {
        this.items.add($index, $item);
    }

    @Override public void addAll(List<CategoryDataModel> $items) {
        this.items.addAll($items);
    }

    @Override public void set(int $index, CategoryDataModel $item) {
        this.items.set($index, $item);
    }

    @Override public void set(List<CategoryDataModel> $items) {
        this.items.clear();
        this.items.addAll($items);
    }

    @Override public CategoryDataModel get(int $index) {
        return this.items.get($index);
    }

    @Override public List<CategoryDataModel> get() {
        return this.items;
    }

    @Override public void remove(int $index) {
        this.items.remove($index);
    }

    @Override public void remove(CategoryDataModel $item) {
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