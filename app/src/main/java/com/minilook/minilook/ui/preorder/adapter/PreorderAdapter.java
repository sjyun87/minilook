package com.minilook.minilook.ui.preorder.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.minilook.minilook.data.model.preorder.PreorderDataModel;
import com.minilook.minilook.data.code.PreorderType;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.preorder.view.coming.viewholder.PreorderComingItemVH;
import com.minilook.minilook.ui.preorder.view.open.viewholder.PreorderOpenItemVH;
import java.util.ArrayList;
import java.util.List;

public class PreorderAdapter extends RecyclerView.Adapter<BaseViewHolder<PreorderDataModel>> implements
    BaseAdapterDataModel<PreorderDataModel>, BaseAdapterDataView<PreorderDataModel> {

    private List<PreorderDataModel> items = new ArrayList<>();

    @NonNull @Override
    public BaseViewHolder<PreorderDataModel> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == PreorderType.TYPE_OPEN.getValue()) {
            return new PreorderOpenItemVH(parent);
        } else if (viewType == PreorderType.TYPE_COMING.getValue()) {
            return new PreorderComingItemVH(parent);
        } else {
            return new BaseViewHolder<>(parent);
        }
    }

    @Override public void onBindViewHolder(@NonNull BaseViewHolder<PreorderDataModel> holder, int position) {
        holder.bind(items.get(position));
    }

    @Override public int getItemViewType(int position) {
        return items.get(position).getFlag();
    }

    @Override public int getItemCount() {
        return getSize();
    }

    @Override public void add(PreorderDataModel $item) {
        this.items.add($item);
    }

    @Override public void add(int $index, PreorderDataModel $item) {
        this.items.add($index, $item);
    }

    @Override public void addAll(List<PreorderDataModel> $items) {
        this.items.addAll($items);
    }

    @Override public void set(int $index, PreorderDataModel $item) {
        this.items.set($index, $item);
    }

    @Override public void set(List<PreorderDataModel> $items) {
        this.items.clear();
        this.items.addAll($items);
    }

    @Override public PreorderDataModel get(int $index) {
        return this.items.get($index);
    }

    @Override public List<PreorderDataModel> get() {
        return this.items;
    }

    @Override public int get(PreorderDataModel $item) {
        return items.indexOf($item);
    }

    @Override public void remove(int $index) {
        this.items.remove($index);
    }

    @Override public void remove(PreorderDataModel $item) {
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
