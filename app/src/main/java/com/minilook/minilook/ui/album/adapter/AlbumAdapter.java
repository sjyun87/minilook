package com.minilook.minilook.ui.album.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.minilook.minilook.data.model.gallery.AlbumDataModel;
import com.minilook.minilook.ui.album.viewholder.AlbumItemVH;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import java.util.ArrayList;
import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumItemVH> implements
    BaseAdapterDataModel<AlbumDataModel>, BaseAdapterDataView<AlbumDataModel> {

    private final List<AlbumDataModel> items = new ArrayList<>();

    @NonNull @Override
    public AlbumItemVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AlbumItemVH(parent);
    }

    @Override public void onBindViewHolder(@NonNull AlbumItemVH holder, int position) {
        holder.bind(items.get(position));
    }

    @Override public int getItemCount() {
        return getSize();
    }

    @Override public void add(AlbumDataModel $item) {
        items.add($item);
    }

    @Override public void add(int $index, AlbumDataModel $item) {
        items.add($index, $item);
    }

    @Override public void addAll(List<AlbumDataModel> $items) {
        items.addAll($items);
    }

    @Override public void set(int $index, AlbumDataModel $item) {
        items.set($index, $item);
    }

    @Override public void set(List<AlbumDataModel> $items) {
        items.clear();
        items.addAll($items);
    }

    @Override public AlbumDataModel get(int $index) {
        return items.get($index);
    }

    @Override public List<AlbumDataModel> get() {
        return items;
    }

    @Override public int get(AlbumDataModel $item) {
        return items.indexOf($item);
    }

    @Override public void remove(int $index) {
        items.remove($index);
    }

    @Override public void remove(AlbumDataModel $item) {
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
