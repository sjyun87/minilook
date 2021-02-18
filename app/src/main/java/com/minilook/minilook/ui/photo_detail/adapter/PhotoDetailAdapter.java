package com.minilook.minilook.ui.photo_detail.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.minilook.minilook.data.model.common.ImageDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.photo_detail.viewholder.PhotoDetailItemVH;
import java.util.ArrayList;
import java.util.List;

public class PhotoDetailAdapter extends RecyclerView.Adapter<PhotoDetailItemVH>
    implements BaseAdapterDataModel<ImageDataModel>, BaseAdapterDataView<ImageDataModel> {

    private final List<ImageDataModel> items = new ArrayList<>();

    @NonNull @Override public PhotoDetailItemVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PhotoDetailItemVH(parent);
    }

    @Override public void onBindViewHolder(@NonNull PhotoDetailItemVH holder, int position) {
        holder.bind(items.get(position));
    }

    @Override public int getItemCount() {
        return getSize();
    }

    @Override public void add(ImageDataModel $item) {
        items.add($item);
    }

    @Override public void add(int $index, ImageDataModel $item) {
        items.add($index, $item);
    }

    @Override public void addAll(List<ImageDataModel> $items) {
        items.addAll($items);
    }

    @Override public void set(int $index, ImageDataModel $item) {
        items.set($index, $item);
    }

    @Override public void set(List<ImageDataModel> $items) {
        items.clear();
        items.addAll($items);
    }

    @Override public ImageDataModel get(int $index) {
        return items.get($index);
    }

    @Override public List<ImageDataModel> get() {
        return items;
    }

    @Override public int get(ImageDataModel $item) {
        return items.indexOf($item);
    }

    @Override public void remove(int $index) {
        items.remove($index);
    }

    @Override public void remove(ImageDataModel $item) {
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
