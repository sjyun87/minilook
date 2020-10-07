package com.minilook.minilook.ui.question.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.minilook.minilook.data.model.question.QuestionDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.question.viewholder.QuestionItemVH;
import java.util.ArrayList;
import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionItemVH>
    implements BaseAdapterDataModel<QuestionDataModel>, BaseAdapterDataView<QuestionDataModel> {

    private List<QuestionDataModel> items = new ArrayList<>();

    @NonNull @Override public QuestionItemVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new QuestionItemVH(parent);
    }

    @Override public void onBindViewHolder(@NonNull QuestionItemVH holder, int position) {
        holder.bind(items.get(position));
    }

    @Override public int getItemCount() {
        return getSize();
    }

    @Override public void add(QuestionDataModel $item) {
        items.add($item);
    }

    @Override public void add(int $index, QuestionDataModel $item) {
        items.add($index, $item);
    }

    @Override public void addAll(List<QuestionDataModel> $items) {
        items.addAll($items);
    }

    @Override public void set(int $index, QuestionDataModel $item) {
        items.set($index, $item);
    }

    @Override public void set(List<QuestionDataModel> $items) {
        items.clear();
        items.addAll($items);
    }

    @Override public QuestionDataModel get(int $index) {
        return items.get($index);
    }

    @Override public List<QuestionDataModel> get() {
        return items;
    }

    @Override public int get(QuestionDataModel $item) {
        return items.indexOf($item);
    }

    @Override public void remove(int $index) {
        items.remove($index);
    }

    @Override public void remove(QuestionDataModel $item) {
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
