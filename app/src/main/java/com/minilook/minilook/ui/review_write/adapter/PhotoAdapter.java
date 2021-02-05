package com.minilook.minilook.ui.review_write.adapter;

import android.text.TextUtils;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.minilook.minilook.data.model.gallery.PhotoDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.review_write.viewholder.PhotoContentItemVH;
import com.minilook.minilook.ui.review_write.viewholder.PhotoFooterItemVH;
import java.util.ArrayList;
import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<BaseViewHolder<PhotoDataModel>> implements
    BaseAdapterDataModel<PhotoDataModel>, BaseAdapterDataView<PhotoDataModel> {

    private static final int TYPE_CONTENT = 0;
    private static final int TYPE_FOOTER = 1;

    private final List<PhotoDataModel> items = new ArrayList<>();

    @NonNull @Override
    public BaseViewHolder<PhotoDataModel> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_CONTENT) {
            return new PhotoContentItemVH(parent);
        } else {
            return new PhotoFooterItemVH(parent);
        }
    }

    @Override public void onBindViewHolder(@NonNull BaseViewHolder<PhotoDataModel> holder, int position) {
        holder.bind(items.get(position));
    }

    @Override public int getItemCount() {
        return getSize();
    }

    @Override public int getItemViewType(int position) {
        if (TextUtils.isEmpty(items.get(position).getName())) {
            return TYPE_FOOTER;
        }
        return TYPE_CONTENT;
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
        notifyItemRangeChanged($start, $row);
    }
}
