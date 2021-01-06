package com.minilook.minilook.ui.album.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.minilook.minilook.R;
import com.minilook.minilook.databinding.ViewGalleryContentsItemBinding;
import com.minilook.minilook.ui.base.BaseViewHolder;

public class GalleryContentsItemVH extends BaseViewHolder<String> {

    @DrawableRes int ph_square = R.drawable.ph_square;

    private final ViewGalleryContentsItemBinding binding;

    public GalleryContentsItemVH(@NonNull View parent) {
        super(ViewGalleryContentsItemBinding.inflate(LayoutInflater.from(parent.getContext()), (ViewGroup) parent,
            false));
        binding = ViewGalleryContentsItemBinding.bind(itemView);
    }

    @Override public void bind(String $data) {
        super.bind($data);

        Glide.with(context)
            .load(data)
            .placeholder(ph_square)
            .error(ph_square)
            .transition(new DrawableTransitionOptions().crossFade())
            .into(binding.imgThumb);

        itemView.setOnClickListener(this::onItemClick);
    }

    void onItemClick(View view) {
    }
}
