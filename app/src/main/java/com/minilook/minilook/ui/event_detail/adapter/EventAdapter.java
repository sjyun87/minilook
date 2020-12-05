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

    private final List<EventDataModel> items = new ArrayList<>();

    @NonNull @Override public EventItemVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EventItemVH(parent, getSize() == 1);
    }

    @Override public void onBindViewHolder(@NonNull EventItemVH holder, int position) {
        holder.bind(items.get(position));
    }

    @Override public int getItemCount() {
        return getSize();
    }

    @Override public void add(EventDataModel $item) {
        items.add($item);
    }

    @Override public void add(int $index, EventDataModel $item) {
        items.add($index, $item);
    }

    @Override public void addAll(List<EventDataModel> $items) {
        items.addAll($items);
    }

    @Override public void set(int $index, EventDataModel $item) {
        items.set($index, $item);
    }

    @Override public void set(List<EventDataModel> $items) {
        items.clear();
        items.addAll($items);
    }

    @Override public EventDataModel get(int $index) {
        return items.get($index);
    }

    @Override public List<EventDataModel> get() {
        return items;
    }

    @Override public int get(EventDataModel $item) {
        return items.indexOf($item);
    }

    @Override public void remove(int $index) {
        items.remove($index);
    }

    @Override public void remove(EventDataModel $item) {
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
