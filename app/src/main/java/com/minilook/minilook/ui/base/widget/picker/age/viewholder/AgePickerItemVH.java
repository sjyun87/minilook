package com.minilook.minilook.ui.base.widget.picker.age.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.minilook.minilook.databinding.ViewAgePickerItemBinding;
import com.minilook.minilook.ui.base.BaseViewHolder;

public class AgePickerItemVH extends BaseViewHolder<String> {

    private final ViewAgePickerItemBinding binding;
    //@Setter private SortSelector.OnSortSelectListener onSortSelectListener;

    public AgePickerItemVH(@NonNull View parent) {
        super(ViewAgePickerItemBinding.inflate(LayoutInflater.from(parent.getContext()), (ViewGroup) parent, false));
        binding = ViewAgePickerItemBinding.bind(itemView);
    }

    @Override public void bind(String $data) {
        super.bind($data);

        binding.txtAge.setText(data);

        itemView.setOnClickListener(this::onItemClick);
    }

    private void onItemClick(View view) {
        //if (onSortSelectListener != null) onSortSelectListener.onSortSelected(data);
    }
}
