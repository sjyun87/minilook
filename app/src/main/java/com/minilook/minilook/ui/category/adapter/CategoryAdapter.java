package com.minilook.minilook.ui.category.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.minilook.minilook.data.model.common.CodeDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.category.viewholder.CategoryVH;
import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryVH> implements
    BaseAdapterDataModel<CodeDataModel>, BaseAdapterDataView<CodeDataModel> {

    private List<CodeDataModel> items = new ArrayList<>();

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

    @Override public void add(CodeDataModel $item) {
        items.add($item);
    }

    @Override public void add(int $index, CodeDataModel $item) {
        items.add($index, $item);
    }

    @Override public void addAll(List<CodeDataModel> $items) {
        items.addAll($items);
    }

    @Override public void set(int $index, CodeDataModel $item) {
        items.set($index, $item);
    }

    @Override public void set(List<CodeDataModel> $items) {
        items.clear();
        items.addAll($items);
    }

    @Override public CodeDataModel get(int $index) {
        return items.get($index);
    }

    @Override public List<CodeDataModel> get() {
        return items;
    }

    @Override public int get(CodeDataModel $item) {
        return items.indexOf($item);
    }

    @Override public void remove(int $index) {
        items.remove($index);
    }

    @Override public void remove(CodeDataModel $item) {
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
