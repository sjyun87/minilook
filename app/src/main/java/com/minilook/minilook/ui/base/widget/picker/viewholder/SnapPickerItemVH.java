package com.minilook.minilook.ui.base.widget.picker.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.minilook.minilook.databinding.ViewSnapPickerItemBinding;
import com.minilook.minilook.ui.base.BaseViewHolder;

public class SnapPickerItemVH extends BaseViewHolder<Integer> {

    private final ViewSnapPickerItemBinding binding;

    public SnapPickerItemVH(@NonNull View parent, boolean isFirst, boolean isEnd) {
        super(ViewSnapPickerItemBinding.inflate(LayoutInflater.from(parent.getContext()), (ViewGroup) parent, false));
        binding = ViewSnapPickerItemBinding.bind(itemView);

        if (isFirst) {
            binding.line1.setVisibility(View.INVISIBLE);
            binding.line2.setVisibility(View.INVISIBLE);
            binding.line4.setVisibility(View.VISIBLE);
            binding.line5.setVisibility(View.VISIBLE);
        } else if (isEnd) {
            binding.line1.setVisibility(View.VISIBLE);
            binding.line2.setVisibility(View.VISIBLE);
            binding.line4.setVisibility(View.INVISIBLE);
            binding.line5.setVisibility(View.INVISIBLE);
        } else {
            binding.line1.setVisibility(View.VISIBLE);
            binding.line2.setVisibility(View.VISIBLE);
            binding.line4.setVisibility(View.VISIBLE);
            binding.line5.setVisibility(View.VISIBLE);
        }
    }

    @Override public void bind(Integer $data) {
        super.bind($data);

        binding.txtContents.setText(String.valueOf(data));
    }

    public void selected() {
        binding.txtContents.setVisibility(View.INVISIBLE);
    }

    public void unselected() {
        binding.txtContents.setVisibility(View.VISIBLE);
    }
}
