package com.minilook.minilook.ui.gallery.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.minilook.minilook.data.model.gallery.PhotoDataModel;
import com.minilook.minilook.ui.gallery.viewholder.SelectedItemVH;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import java.util.ArrayList;
import java.util.List;

public class SelectedAdapter extends RecyclerView.Adapter<SelectedItemVH> implements
    BaseAdapterDataModel<PhotoDataModel>, BaseAdapterDataView<PhotoDataModel> {

    private final List<PhotoDataModel> items = new ArrayList<>();

    @NonNull @Override
    public SelectedItemVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SelectedItemVH(parent);
    }

    @Override public void onBindViewHolder(@NonNull SelectedItemVH holder, int position) {
        holder.bind(items.get(position));
    }

    @Override public int getItemCount() {
        return getSize();
    }

    @Override public void add(PhotoDataModel $item) {
        items.add($item);
    }

    @Override public void add(int $index, PhotoDataModel $item) {
        items.add($index, $item);
    }

    @Override public void addAll(List<PhotoDataModel> $items) {
        items.addAll($items);
    }

    @Override public void set(int $index, PhotoDataModel $item) {
        items.set($index, $item);
    }

    @Override public void set(List<PhotoDataModel> $items) {
        items.clear();
        items.addAll($items);
    }

    @Override public PhotoDataModel get(int $index) {
        return items.get($index);
    }

    @Override public List<PhotoDataModel> get() {
        return items;
    }

    @Override public int get(PhotoDataModel $item) {
        return items.indexOf($item);
    }

    @Override public void remove(int $index) {
        items.remove($index);
    }

    @Override public void remove(PhotoDataModel $item) {
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
