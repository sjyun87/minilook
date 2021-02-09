package com.minilook.minilook.ui.gallery.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.gallery.AlbumDataModel;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.databinding.ViewAlbumItemBinding;
import com.minilook.minilook.ui.base.BaseViewHolder;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class AlbumItemVH extends BaseViewHolder<AlbumDataModel> {

    private final ViewAlbumItemBinding binding;

    @DrawableRes int ph_square = R.drawable.ph_square;

    public AlbumItemVH(@NonNull View parent) {
        super(ViewAlbumItemBinding.inflate(LayoutInflater.from(parent.getContext()), (ViewGroup) parent, false));
        binding = ViewAlbumItemBinding.bind(itemView);
    }

    @Override public void bind(AlbumDataModel $data) {
        super.bind($data);

        Glide.with(context)
            .load(data.getRecentImage())
            .placeholder(ph_square)
            .error(ph_square)
            .transition(new DrawableTransitionOptions().crossFade())
            .into(binding.imgThumb);

        binding.txtName.setText(data.getName());
        binding.txtCount.setText(String.valueOf(data.getCount()));

        itemView.setOnClickListener(this::onItemClick);
    }

    void onItemClick(View view) {
        RxBus.send(new RxBusEventAlbumSelected(data));
    }

    @AllArgsConstructor @Getter public final static class RxBusEventAlbumSelected {
        private final AlbumDataModel data;
    }
}

