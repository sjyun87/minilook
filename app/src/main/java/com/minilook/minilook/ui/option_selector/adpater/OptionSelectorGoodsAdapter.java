package com.minilook.minilook.ui.option_selector.adpater;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.minilook.minilook.data.model.product.GoodsDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.option_selector.viewholder.OptionSelectorGoodsItemVH;
import java.util.ArrayList;
import java.util.List;
import lombok.Setter;

public class OptionSelectorGoodsAdapter extends RecyclerView.Adapter<OptionSelectorGoodsItemVH>
    implements BaseAdapterDataModel<GoodsDataModel>, BaseAdapterDataView<GoodsDataModel> {

    private List<GoodsDataModel> items = new ArrayList<>();
    @Setter private OptionSelectorGoodsItemVH.OnButtonClickListener OnButtonClickListener;

    @NonNull @Override public OptionSelectorGoodsItemVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OptionSelectorGoodsItemVH(parent);
    }

    @Override public void onBindViewHolder(@NonNull OptionSelectorGoodsItemVH holder, int position) {
        holder.bind(items.get(position));
        holder.setOnButtonClickListener(OnButtonClickListener);
    }

    @Override public int getItemCount() {
        return getSize();
    }

    @Override public void add(GoodsDataModel $item) {
        this.items.add($item);
    }

    @Override public void add(int $index, GoodsDataModel $item) {
        this.items.add($index, $item);
    }

    @Override public void addAll(List<GoodsDataModel> $items) {
        this.items.addAll($items);
    }

    @Override public void set(int $index, GoodsDataModel $item) {
        this.items.set($index, $item);
    }

    @Override public void set(List<GoodsDataModel> $items) {
        this.items.clear();
        this.items.addAll($items);
    }

    @Override public GoodsDataModel get(int $index) {
        return this.items.get($index);
    }

    @Override public List<GoodsDataModel> get() {
        return this.items;
    }

    @Override public int get(GoodsDataModel $item) {
        return items.indexOf($item);
    }

    @Override public void remove(int $index) {
        this.items.remove($index);
    }

    @Override public void remove(GoodsDataModel $item) {
        items.remove($item);
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
