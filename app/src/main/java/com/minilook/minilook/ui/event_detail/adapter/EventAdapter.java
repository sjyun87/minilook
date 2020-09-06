package com.minilook.minilook.ui.event_detail.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.event_detail.viewholder.EventItemVH;
import java.util.ArrayList;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventItemVH>
    implements BaseAdapterDataModel<String>, BaseAdapterDataView<String> {

    private List<String> items = new ArrayList<>();

    @NonNull @Override public EventItemVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EventItemVH(parent);
    }

    @Override public void onBindViewHolder(@NonNull EventItemVH holder, int position) {
        holder.bind(items.get(position));
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

    @Override public int get(String $item) {
        return items.indexOf($item);
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
