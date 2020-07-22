package com.minilook.minilook.ui.main.fragment.lookbook.view.preview.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minilook.minilook.data.model.lookbook.LookBookDataModel;
import com.minilook.minilook.data.type.LookBookModuleType;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.main.fragment.lookbook.view.preview.viewholder.LookBookImageModuleVH;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class LookBookModuleAdapter extends RecyclerView.Adapter<BaseViewHolder> implements
    BaseAdapterDataModel<LookBookDataModel>, BaseAdapterDataView<LookBookDataModel> {

    private List<LookBookDataModel> items = new ArrayList<>();

    @NonNull @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == LookBookModuleType.TYPE_IMAGE.getValue()) {
            return new LookBookImageModuleVH(parent);
        } else {
            Timber.e("Type is null -");
            return new BaseViewHolder(parent);
        }
    }

    @SuppressWarnings("unchecked")
    @Override public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.bind(items.get(position).getPreview());
    }

    @Override public int getItemCount() {
        return getSize();
    }

    @Override public int getItemViewType(int position) {
        return Integer.parseInt(items.get(position).getType());
    }

    @Override public void add(LookBookDataModel $item) {
        this.items.add($item);
    }

    @Override public void add(int $index, LookBookDataModel $item) {
        this.items.add($index, $item);
    }

    @Override public void addAll(List<LookBookDataModel> $items) {
        this.items.addAll($items);
    }

    @Override public void set(int $index, LookBookDataModel $item) {
        this.items.set($index, $item);
    }

    @Override public void set(List<LookBookDataModel> $items) {
        this.items.clear();
        this.items.addAll($items);
    }

    @Override public LookBookDataModel get(int $index) {
        return this.items.get($index);
    }

    @Override public List<LookBookDataModel> get() {
        return this.items;
    }

    @Override public void remove(int $index) {
        this.items.remove($index);
    }

    @Override public void remove(LookBookDataModel $item) {
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
