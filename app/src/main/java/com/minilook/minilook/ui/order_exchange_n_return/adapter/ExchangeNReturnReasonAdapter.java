package com.minilook.minilook.ui.order_exchange_n_return.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.minilook.minilook.data.model.order.CodeDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.order_exchange_n_return.viewholder.ExchangeNReturnReasonItemVH;
import java.util.ArrayList;
import java.util.List;

public class ExchangeNReturnReasonAdapter extends RecyclerView.Adapter<ExchangeNReturnReasonItemVH>
    implements BaseAdapterDataModel<CodeDataModel>, BaseAdapterDataView<CodeDataModel> {

    private List<CodeDataModel> items = new ArrayList<>();

    @NonNull @Override public ExchangeNReturnReasonItemVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ExchangeNReturnReasonItemVH(parent);
    }

    @Override public void onBindViewHolder(@NonNull ExchangeNReturnReasonItemVH holder, int position) {
        holder.bind(items.get(position));
    }

    @Override public int getItemCount() {
        return getSize();
    }

    @Override public void add(CodeDataModel $item) {
        this.items.add($item);
    }

    @Override public void add(int $index, CodeDataModel $item) {
        this.items.add($index, $item);
    }

    @Override public void addAll(List<CodeDataModel> $items) {
        this.items.addAll($items);
    }

    @Override public void set(int $index, CodeDataModel $item) {
        this.items.set($index, $item);
    }

    @Override public void set(List<CodeDataModel> $items) {
        this.items.clear();
        this.items.addAll($items);
    }

    @Override public CodeDataModel get(int $index) {
        return this.items.get($index);
    }

    @Override public List<CodeDataModel> get() {
        return this.items;
    }

    @Override public int get(CodeDataModel $item) {
        return items.indexOf($item);
    }

    @Override public void remove(int $index) {
        this.items.remove($index);
    }

    @Override public void remove(CodeDataModel $item) {
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
