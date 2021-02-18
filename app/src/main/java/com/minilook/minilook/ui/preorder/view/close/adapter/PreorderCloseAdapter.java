package com.minilook.minilook.ui.preorder.view.close.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.minilook.minilook.data.model.preorder.PreorderDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.preorder.view.close.viewholder.PreorderCloseItemVH;
import java.util.ArrayList;
import java.util.List;

public class PreorderCloseAdapter extends RecyclerView.Adapter<PreorderCloseItemVH> implements
    BaseAdapterDataModel<PreorderDataModel>, BaseAdapterDataView<PreorderDataModel> {

    private List<PreorderDataModel> items = new ArrayList<>();

    @NonNull @Override
    public PreorderCloseItemVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PreorderCloseItemVH(parent);
    }

    @Override public void onBindViewHolder(@NonNull PreorderCloseItemVH holder, int position) {
        holder.bind(items.get(position));
    }

    @Override public int getItemCount() {
        return getSize();
    }

    @Override public void add(PreorderDataModel $item) {
        items.add($item);
    }

    @Override public void add(int $index, PreorderDataModel $item) {
        items.add($index, $item);
    }

    @Override public void addAll(List<PreorderDataModel> $items) {
        items.addAll($items);
    }

    @Override public void set(int $index, PreorderDataModel $item) {
        items.set($index, $item);
    }

    @Override public void set(List<PreorderDataModel> $items) {
        items.clear();
        items.addAll($items);
    }

    @Override public PreorderDataModel get(int $index) {
        return items.get($index);
    }

    @Override public List<PreorderDataModel> get() {
        return items;
    }

    @Override public int get(PreorderDataModel $item) {
        return items.indexOf($item);
    }

    @Override public void remove(int $index) {
        items.remove($index);
    }

    @Override public void remove(PreorderDataModel $item) {
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
        notifyItemRangeInserted($start, $row);
    }
}
