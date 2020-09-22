package com.minilook.minilook.ui.lookbook.view.preview.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.minilook.minilook.data.model.lookbook.LookBookModuleDataModel;
import com.minilook.minilook.data.code.LookBookModuleType;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.lookbook.view.preview.viewholder.LookBookImageModuleVH;
import java.util.ArrayList;
import java.util.List;
import timber.log.Timber;

public class LookBookModuleAdapter extends RecyclerView.Adapter<BaseViewHolder<String>> implements
    BaseAdapterDataModel<LookBookModuleDataModel>, BaseAdapterDataView<LookBookModuleDataModel> {

    private List<LookBookModuleDataModel> items = new ArrayList<>();

    @NonNull @Override
    public BaseViewHolder<String> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == LookBookModuleType.TYPE_IMAGE.getValue()) {
            return new LookBookImageModuleVH(parent);
        } else {
            Timber.e("Lookbook Module type is null..");
            return new BaseViewHolder<>(parent);
        }
    }

    @SuppressWarnings("unchecked")
    @Override public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.bind(items.get(position).getBackground_url());
    }

    @Override public int getItemCount() {
        return getSize();
    }

    @Override public int getItemViewType(int position) {
        return items.get(position).getType();
    }

    @Override public void add(LookBookModuleDataModel $item) {
        this.items.add($item);
    }

    @Override public void add(int $index, LookBookModuleDataModel $item) {
        this.items.add($index, $item);
    }

    @Override public void addAll(List<LookBookModuleDataModel> $items) {
        this.items.addAll($items);
    }

    @Override public void set(int $index, LookBookModuleDataModel $item) {
        this.items.set($index, $item);
    }

    @Override public void set(List<LookBookModuleDataModel> $items) {
        this.items.clear();
        this.items.addAll($items);
    }

    @Override public LookBookModuleDataModel get(int $index) {
        return this.items.get($index);
    }

    @Override public List<LookBookModuleDataModel> get() {
        return this.items;
    }

    @Override public int get(LookBookModuleDataModel $item) {
        return items.indexOf($item);
    }

    @Override public void remove(int $index) {
        this.items.remove($index);
    }

    @Override public void remove(LookBookModuleDataModel $item) {
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
