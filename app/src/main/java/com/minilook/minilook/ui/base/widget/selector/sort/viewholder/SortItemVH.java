package com.minilook.minilook.ui.base.widget.selector.sort.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.minilook.minilook.data.model.common.CodeDataModel;
import com.minilook.minilook.databinding.ViewSortSelectorItemBinding;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.base.widget.selector.sort.SortSelector;
import lombok.Setter;

public class SortItemVH extends BaseViewHolder<CodeDataModel> {

    private final ViewSortSelectorItemBinding binding;
    @Setter private SortSelector.OnSortSelectListener onSortSelectListener;

    public SortItemVH(@NonNull View parent) {
        super(ViewSortSelectorItemBinding.inflate(LayoutInflater.from(parent.getContext()), (ViewGroup) parent, false));
        binding = ViewSortSelectorItemBinding.bind(itemView);
    }

    @Override public void bind(CodeDataModel $data) {
        super.bind($data);

        binding.txtSort.setText(data.getName());

        itemView.setOnClickListener(this::onItemClick);
    }

    private void onItemClick(View view) {
        if (onSortSelectListener != null) onSortSelectListener.onSortSelected(data);
    }
}
