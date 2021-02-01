package com.minilook.minilook.ui.album.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.gallery.GalleryDataModel;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.databinding.ViewGallerySelectedItemBinding;
import com.minilook.minilook.ui.base.BaseViewHolder;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class SelectedItemVH extends BaseViewHolder<GalleryDataModel> {

    @DrawableRes int ph_square = R.drawable.ph_square;

    private final ViewGallerySelectedItemBinding binding;

    public SelectedItemVH(@NonNull View parent) {
        super(ViewGallerySelectedItemBinding.inflate(LayoutInflater.from(parent.getContext()), (ViewGroup) parent,
            false));
        binding = ViewGallerySelectedItemBinding.bind(itemView);
    }

    @Override public void bind(GalleryDataModel $data) {
        super.bind($data);

        Glide.with(context)
            .load(data.getPath())
            .placeholder(ph_square)
            .error(ph_square)
            .transition(new DrawableTransitionOptions().crossFade())
            .into(binding.imgThumb);

        itemView.setOnClickListener(this::onItemClick);
    }

    void onItemClick(View view) {
        RxBus.send(new RxEventGallerySelectedImageClick(data));
    }

    @AllArgsConstructor @Getter public final static class RxEventGallerySelectedImageClick {
        private final GalleryDataModel model;
    }
}
