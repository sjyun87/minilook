package com.minilook.minilook.ui.preorder_detail.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.preorder_detail.viewholder.PreorderDetailImageVH;
import java.util.ArrayList;
import java.util.List;

public class PreorderDetailImageAdapter extends RecyclerView.Adapter<PreorderDetailImageVH>
    implements BaseAdapterDataModel<String>, BaseAdapterDataView<String> {

    private List<String> items = new ArrayList<>();

    @NonNull @Override public PreorderDetailImageVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PreorderDetailImageVH(parent);
    }

    @Override public void onBindViewHolder(@NonNull PreorderDetailImageVH holder, int position) {
        holder.bind(items.get(position));
    }

    @Override public int getItemCount() {
        return getSize();
    }

    @Override public void add(String $item) {
        items.add($item);
    }

    @Override public void add(int $index, String $item) {
        items.add($index, $item);
    }

    @Override public void addAll(List<String> $items) {
        items.addAll($items);
    }

    @Override public void set(int $index, String $item) {
        items.set($index, $item);
    }

    @Override public void set(List<String> $items) {
        items.clear();
        items.addAll($items);
    }

    @Override public String get(int $index) {
        return items.get($index);
    }

    @Override public List<String> get() {
        return items;
    }

    @Override public int get(String $item) {
        return items.indexOf($item);
    }

    @Override public void remove(int $index) {
        items.remove($index);
    }

    @Override public void remove(String $item) {
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
