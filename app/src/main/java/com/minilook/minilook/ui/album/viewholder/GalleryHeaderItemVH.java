package com.minilook.minilook.ui.album.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.minilook.minilook.data.model.gallery.PhotoDataModel;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.databinding.ViewGalleryHeaderItemBinding;
import com.minilook.minilook.ui.base.BaseViewHolder;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class GalleryHeaderItemVH extends BaseViewHolder<PhotoDataModel> {

    public GalleryHeaderItemVH(@NonNull View parent) {
        super(ViewGalleryHeaderItemBinding.inflate(LayoutInflater.from(parent.getContext()), (ViewGroup) parent,
            false));
    }

    @Override public void bind(PhotoDataModel $data) {
        super.bind($data);

        itemView.setOnClickListener(this::onItemClick);
    }

    void onItemClick(View view) {
        RxBus.send(new RxBusEventNavigateToCamera());
    }

    @AllArgsConstructor @Getter public final static class RxBusEventNavigateToCamera {
    }
}
