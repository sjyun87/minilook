package com.minilook.minilook.ui.main.fragment.lookbook.view.preview.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.lookbook.LookBookModuleDataModel;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.ui.base.BaseViewHolder;

import butterknife.BindView;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class LookBookImageModuleVH extends BaseViewHolder<LookBookModuleDataModel> {

    @BindView(R.id.img_lookbook) ImageView bgImageView;

    public LookBookImageModuleVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_lookbook_image, (ViewGroup) itemView, false));
    }

    @Override public void bind(LookBookModuleDataModel $data) {
        super.bind($data);

        Glide.with(itemView)
            .load(data.getBg_url())
            .into(bgImageView);

        itemView.setOnClickListener(v -> RxBus.send(new RxEventPreviewClick()));
    }

    @AllArgsConstructor @Getter public final static class RxEventPreviewClick {
    }
}
