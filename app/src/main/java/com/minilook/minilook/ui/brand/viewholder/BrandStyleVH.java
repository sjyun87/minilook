package com.minilook.minilook.ui.brand.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.FontRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.common.CodeDataModel;
import com.minilook.minilook.databinding.ViewBrandStyleBinding;
import com.minilook.minilook.ui.base.BaseViewHolder;
import lombok.Setter;

public class BrandStyleVH extends BaseViewHolder<CodeDataModel> {

    @DrawableRes int img_style_off = R.drawable.bg_brand_style_off;
    @DrawableRes int img_style_on = R.drawable.bg_brand_style_on;

    @ColorRes int color_FF8140E5 = R.color.color_FF8140E5;
    @ColorRes int color_FF232323 = R.color.color_FF232323;

    @FontRes int font_regular = R.font.nanum_square_r;
    @FontRes int font_bold = R.font.nanum_square_b;

    @StringRes int str_format_tag = R.string.base_tag;

    @Setter private OnStyleClickListener onStyleClickListener;

    private final ViewBrandStyleBinding binding;

    public BrandStyleVH(@NonNull View parent) {
        super(ViewBrandStyleBinding.inflate(LayoutInflater.from(parent.getContext()), (ViewGroup) parent, false));
        binding = ViewBrandStyleBinding.bind(itemView);
    }

    @Override public void bind(CodeDataModel $data) {
        super.bind($data);

        binding.txtStyle.setText(String.format(resources.getString(str_format_tag), data.getName()));

        if (data.isSelected()) {
            binding.txtStyle.setBackgroundResource(img_style_on);
            binding.txtStyle.setTextColor(resources.getColor(color_FF8140E5));
            binding.txtStyle.setTypeface(resources.getFont(font_bold));
        } else {
            binding.txtStyle.setBackgroundResource(img_style_off);
            binding.txtStyle.setTextColor(resources.getColor(color_FF232323));
            binding.txtStyle.setTypeface(resources.getFont(font_regular));
        }

        itemView.setOnClickListener(this::onItemClick);
    }

    void onItemClick(View view) {
        if (onStyleClickListener != null) onStyleClickListener.onStyleClick(data);
    }

    public interface OnStyleClickListener {
        void onStyleClick(CodeDataModel data);
    }
}
