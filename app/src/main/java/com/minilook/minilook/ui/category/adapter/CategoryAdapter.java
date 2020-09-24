package com.minilook.minilook.ui.category.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minilook.minilook.data.model.common.CategoryDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.category.viewholder.CategoryVH;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryVH> implements
    BaseAdapterDataModel<CategoryDataModel>, BaseAdapterDataView<CategoryDataModel> {

    private List<CategoryDataModel> items = new ArrayList<>();

    @NonNull @Override
    public CategoryVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryVH(parent);
    }

    @Override public void onBindViewHolder(@NonNull CategoryVH holder, int position) {
        holder.bind(items.get(position));
    }

    @Override public int getItemCount() {
        return getSize();
    }

    @Override public void add(CategoryDataModel $item) {
        items.add($item);
    }

    @Override public void add(int $index, CategoryDataModel $item) {
        items.add($index, $item);
    }

    @Override public void addAll(List<CategoryDataModel> $items) {
        items.addAll($items);
    }

    @Override public void set(int $index, CategoryDataModel $item) {
        items.set($index, $item);
    }

    @Override public void set(List<CategoryDataModel> $items) {
        items.clear();
        items.addAll($items);
    }

    @Override public CategoryDataModel get(int $index) {
        return items.get($index);
    }

    @Override public List<CategoryDataModel> get() {
        return items;
    }

    @Override public int get(CategoryDataModel $item) {
        return items.indexOf($item);
    }

    @Override public void remove(int $index) {
        items.remove($index);
    }

    @Override public void remove(CategoryDataModel $item) {
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
