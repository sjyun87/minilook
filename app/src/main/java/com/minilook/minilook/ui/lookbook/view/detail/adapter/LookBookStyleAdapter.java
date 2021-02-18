package com.minilook.minilook.ui.lookbook.view.detail.adapter;

import android.text.TextUtils;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.lookbook.view.detail.viewholder.LookBookStyleVH;
import java.util.ArrayList;
import java.util.List;

public class LookBookStyleAdapter extends RecyclerView.Adapter<LookBookStyleVH>
    implements BaseAdapterDataModel<String>, BaseAdapterDataView<String> {

    private final List<String> items = new ArrayList<>();

    @NonNull @Override public LookBookStyleVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LookBookStyleVH(parent);
    }

    @Override public void onBindViewHolder(@NonNull LookBookStyleVH holder, int position) {
        holder.bind(items.get(position));
    }

    @Override public int getItemCount() {
        return getSize();
    }

    @Override public void add(String $item) {
        if (!TextUtils.isEmpty($item)) items.add($item);
    }

    @Override public void add(int $index, String $item) {
        if (!TextUtils.isEmpty($item)) items.add($index, $item);
    }

    @Override public void addAll(List<String> $items) {
        for (String item : $items) add(item);
    }

    @Override public void set(int $index, String $item) {
        if (!TextUtils.isEmpty($item)) items.set($index, $item);
    }

    @Override public void set(List<String> $items) {
        items.clear();
        for (String item : $items) add(item);
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
