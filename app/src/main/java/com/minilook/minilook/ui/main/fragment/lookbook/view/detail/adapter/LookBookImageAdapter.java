package com.minilook.minilook.ui.main.fragment.lookbook.view.detail.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.main.fragment.lookbook.view.detail.viewholder.LookBookImageVH;

import java.util.ArrayList;
import java.util.List;

public class LookBookImageAdapter extends RecyclerView.Adapter<LookBookImageVH>
    implements BaseAdapterDataModel<String>, BaseAdapterDataView<String> {

    private List<String> items = new ArrayList<>();

    @NonNull @Override public LookBookImageVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LookBookImageVH(parent);
    }

    @Override public void onBindViewHolder(@NonNull LookBookImageVH holder, int position) {
        holder.bind(items.get(position), position);
    }

    @Override public int getItemCount() {
        return getSize();
    }

    @Override public void add(String $item) {
        this.items.add($item);
    }

    @Override public void add(int $index, String $item) {
        this.items.add($index, $item);
    }

    @Override public void addAll(List<String> $items) {
        this.items.addAll($items);
    }

    @Override public void set(int $index, String $item) {
        this.items.set($index, $item);
    }

    @Override public void set(List<String> $items) {
        this.items.clear();
        this.items.addAll($items);
    }

    @Override public String get(int $index) {
        return this.items.get($index);
    }

    @Override public List<String> get() {
        return this.items;
    }

    @Override public void remove(int $index) {
        this.items.remove($index);
    }

    @Override public void remove(String $item) {
        this.remove($item);
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
