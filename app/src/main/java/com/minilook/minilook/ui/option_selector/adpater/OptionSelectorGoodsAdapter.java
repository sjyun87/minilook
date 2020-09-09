package com.minilook.minilook.ui.option_selector.adpater;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.minilook.minilook.data.model.order.OrderOptionDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.option_selector.viewholder.OptionSelectorGoodsItemVH;
import java.util.ArrayList;
import java.util.List;
import lombok.Setter;

public class OptionSelectorGoodsAdapter extends RecyclerView.Adapter<OptionSelectorGoodsItemVH>
    implements BaseAdapterDataModel<OrderOptionDataModel>, BaseAdapterDataView<OrderOptionDataModel> {

    private List<OrderOptionDataModel> items = new ArrayList<>();
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

    @Override public void add(OrderOptionDataModel $item) {
        this.items.add($item);
    }

    @Override public void add(int $index, OrderOptionDataModel $item) {
        this.items.add($index, $item);
    }

    @Override public void addAll(List<OrderOptionDataModel> $items) {
        this.items.addAll($items);
    }

    @Override public void set(int $index, OrderOptionDataModel $item) {
        this.items.set($index, $item);
    }

    @Override public void set(List<OrderOptionDataModel> $items) {
        this.items.clear();
        this.items.addAll($items);
    }

    @Override public OrderOptionDataModel get(int $index) {
        return this.items.get($index);
    }

    @Override public List<OrderOptionDataModel> get() {
        return this.items;
    }

    @Override public int get(OrderOptionDataModel $item) {
        return items.indexOf($item);
    }

    @Override public void remove(int $index) {
        this.items.remove($index);
    }

    @Override public void remove(OrderOptionDataModel $item) {
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