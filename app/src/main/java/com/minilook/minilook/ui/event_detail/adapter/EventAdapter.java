package com.minilook.minilook.ui.event_detail.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.minilook.minilook.data.model.event.EventDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.event_detail.viewholder.EventItemVH;
import java.util.ArrayList;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventItemVH>
    implements BaseAdapterDataModel<EventDataModel>, BaseAdapterDataView<EventDataModel> {

    private List<EventDataModel> items = new ArrayList<>();

    @NonNull @Override public EventItemVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EventItemVH(parent);
    }

    @Override public void onBindViewHolder(@NonNull EventItemVH holder, int position) {
        holder.bind(items.get(position));
    }

    @Override public int getItemCount() {
        return getSize();
    }

    @Override public void add(EventDataModel $item) {
        this.items.add($item);
    }

    @Override public void add(int $index, EventDataModel $item) {
        this.items.add($index, $item);
    }

    @Override public void addAll(List<EventDataModel> $items) {
        this.items.addAll($items);
    }

    @Override public void set(int $index, EventDataModel $item) {
        this.items.set($index, $item);
    }

    @Override public void set(List<EventDataModel> $items) {
        this.items.clear();
        this.items.addAll($items);
    }

    @Override public EventDataModel get(int $index) {
        return this.items.get($index);
    }

    @Override public List<EventDataModel> get() {
        return this.items;
    }

    @Override public int get(EventDataModel $item) {
        return items.indexOf($item);
    }

    @Override public void remove(int $index) {
        this.items.remove($index);
    }

    @Override public void remove(EventDataModel $item) {
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