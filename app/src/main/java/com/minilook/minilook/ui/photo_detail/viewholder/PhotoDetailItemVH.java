package com.minilook.minilook.ui.photo_detail.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.common.ImageDataModel;
import com.minilook.minilook.databinding.ViewPhotoItemBinding;
import com.minilook.minilook.ui.base.BaseViewHolder;

public class PhotoDetailItemVH extends BaseViewHolder<ImageDataModel> {

    @DrawableRes int ph_square = R.drawable.ph_square;

    private final ViewPhotoItemBinding binding;

    public PhotoDetailItemVH(@NonNull View parent) {
        super(ViewPhotoItemBinding.inflate(LayoutInflater.from(parent.getContext()), (ViewGroup) parent,
            false));
        binding = ViewPhotoItemBinding.bind(itemView);
    }

    @Override
    public void bind(ImageDataModel $data) {
        super.bind($data);

        Glide.with(context)
            .load(data.getOriginUrl())
            .error(ph_square)
            .transition(new DrawableTransitionOptions().crossFade())
            .into(binding.imgContents);
    }
}
