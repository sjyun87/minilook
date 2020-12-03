package com.minilook.minilook.ui.lookbook.view.detail.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.minilook.minilook.R;
import com.minilook.minilook.databinding.ViewLookbookDetailStyleBinding;
import com.minilook.minilook.ui.base.BaseViewHolder;

public class LookBookStyleVH extends BaseViewHolder<String> {

    @DrawableRes int ph_square = R.drawable.ph_square;

    private ViewLookbookDetailStyleBinding binding;

    public LookBookStyleVH(@NonNull View parent) {
        super(ViewLookbookDetailStyleBinding.inflate(LayoutInflater.from(parent.getContext()), (ViewGroup) parent,
            false));
        binding = ViewLookbookDetailStyleBinding.bind(itemView);
    }

    @Override public void bind(String $data) {
        super.bind($data);

        Glide.with(context)
            .load(data)
            .placeholder(ph_square)
            .error(ph_square)
            .transition(new DrawableTransitionOptions().crossFade())
            .into(binding.imgStyle);

        itemView.setOnClickListener(this::onItemClick);
    }

    void onItemClick(View view) {
        // 갤러리
    }
}
