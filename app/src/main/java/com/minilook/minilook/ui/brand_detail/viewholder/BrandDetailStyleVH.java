package com.minilook.minilook.ui.brand_detail.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.minilook.minilook.R;
import com.minilook.minilook.databinding.ViewBrandDetailStyleBinding;
import com.minilook.minilook.ui.base.BaseViewHolder;

public class BrandDetailStyleVH extends BaseViewHolder<String> {

    @DrawableRes int ph_square = R.drawable.ph_square;

    private final ViewBrandDetailStyleBinding binding;

    public BrandDetailStyleVH(@NonNull View parent) {
        super(ViewBrandDetailStyleBinding.inflate(LayoutInflater.from(parent.getContext()), (ViewGroup) parent,
            false));
        binding = ViewBrandDetailStyleBinding.bind(itemView);
    }

    @Override public void bind(String $data) {
        super.bind($data);

        Glide.with(context)
            .load(data)
            .placeholder(ph_square)
            .error(ph_square)
            .transition(new DrawableTransitionOptions().crossFade())
            .into(binding.imgStyle);
    }
}
