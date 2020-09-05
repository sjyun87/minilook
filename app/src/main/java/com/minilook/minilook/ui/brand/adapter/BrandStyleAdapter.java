package com.minilook.minilook.ui.brand.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.minilook.minilook.data.model.common.StyleDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.brand.viewholder.BrandStyleVH;
import java.util.ArrayList;
import java.util.List;
import lombok.Setter;

public class BrandStyleAdapter extends RecyclerView.Adapter<BrandStyleVH> implements
    BaseAdapterDataModel<StyleDataModel>, BaseAdapterDataView<StyleDataModel> {

    private List<StyleDataModel> items = new ArrayList<>();
    @Setter private BrandStyleVH.OnStyleClickListener onStyleClickListener;

    @NonNull @Override
    public BrandStyleVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BrandStyleVH(parent);
    }

    @Override public void onBindViewHolder(@NonNull BrandStyleVH holder, int position) {
        holder.bind(items.get(position));
        holder.setOnStyleClickListener(onStyleClickListener);
    }

    @Override public int getItemCount() {
        return getSize();
    }

    @Override public void add(StyleDataModel $item) {
        this.items.add($item);
    }

    @Override public void add(int $index, StyleDataModel $item) {
        this.items.add($index, $item);
    }

    @Override public void addAll(List<StyleDataModel> $items) {
        this.items.addAll($items);
    }

    @Override public void set(int $index, StyleDataModel $item) {
        this.items.set($index, $item);
    }

    @Override public void set(List<StyleDataModel> $items) {
        this.items.clear();
        this.items.addAll($items);
    }

    @Override public StyleDataModel get(int $index) {
        return this.items.get($index);
    }

    @Override public List<StyleDataModel> get() {
        return this.items;
    }

    @Override public int get(StyleDataModel $item) {
        return items.indexOf($item);
    }

    @Override public void remove(int $index) {
        this.items.remove($index);
    }

    @Override public void remove(StyleDataModel $item) {
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
