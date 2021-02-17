package com.minilook.minilook.ui.photo.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.image.ImageDataModel;
import com.minilook.minilook.databinding.ViewPhotoItemBinding;
import com.minilook.minilook.ui.base.BaseViewHolder;

public class PhotoItemVH extends BaseViewHolder<ImageDataModel> {

    @DrawableRes int ph_square = R.drawable.ph_square;

    private final ViewPhotoItemBinding binding;

    public PhotoItemVH(@NonNull View parent) {
        super(ViewPhotoItemBinding.inflate(LayoutInflater.from(parent.getContext()), (ViewGroup) parent,
            false));
        binding = ViewPhotoItemBinding.bind(itemView);
    }

    @Override
    public void bind(ImageDataModel $data) {
        super.bind($data);

        Glide.with(context)
            .load(data.getOriginUrl())
            .placeholder(ph_square)
            .error(ph_square)
            .transition(new DrawableTransitionOptions().crossFade())
            .into(binding.imgContents);
    }
}
