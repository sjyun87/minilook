package com.minilook.minilook.ui.challenge.view.close.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.minilook.minilook.data.model.challenge.ChallengeDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.challenge.view.close.viewholder.ChallengeCloseItemVH;
import java.util.ArrayList;
import java.util.List;

public class ChallengeCloseAdapter extends RecyclerView.Adapter<ChallengeCloseItemVH> implements
    BaseAdapterDataModel<ChallengeDataModel>, BaseAdapterDataView<ChallengeDataModel> {

    private final List<ChallengeDataModel> items = new ArrayList<>();

    @NonNull @Override public ChallengeCloseItemVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChallengeCloseItemVH(parent);
    }

    @Override public void onBindViewHolder(@NonNull ChallengeCloseItemVH holder, int position) {
        holder.bind(items.get(position));
    }

    @Override public int getItemCount() {
        return getSize();
    }

    @Override public void add(ChallengeDataModel $item) {
        items.add($item);
    }

    @Override public void add(int $index, ChallengeDataModel $item) {
        items.add($index, $item);
    }

    @Override public void addAll(List<ChallengeDataModel> $items) {
        items.addAll($items);
    }

    @Override public void set(int $index, ChallengeDataModel $item) {
        items.set($index, $item);
    }

    @Override public void set(List<ChallengeDataModel> $items) {
        items.clear();
        items.addAll($items);
    }

    @Override public ChallengeDataModel get(int $index) {
        return items.get($index);
    }

    @Override public List<ChallengeDataModel> get() {
        return items;
    }

    @Override public int get(ChallengeDataModel $item) {
        return items.indexOf($item);
    }

    @Override public void remove(int $index) {
        items.remove($index);
    }

    @Override public void remove(ChallengeDataModel $item) {
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
