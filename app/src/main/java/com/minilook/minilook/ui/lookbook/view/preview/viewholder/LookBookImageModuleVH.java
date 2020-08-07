package com.minilook.minilook.ui.lookbook.view.preview.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import butterknife.BindView;
import com.bumptech.glide.Glide;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.lookbook.LookBookPreviewDataModel;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.lookbook.LookBookPresenterImpl;

public class LookBookImageModuleVH extends BaseViewHolder<LookBookPreviewDataModel> {

    @BindView(R.id.img_lookbook) ImageView bgImageView;

    public LookBookImageModuleVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_lookbook_image, (ViewGroup) itemView, false));
    }

    @Override public void bind(LookBookPreviewDataModel $data) {
        super.bind($data);

        Glide.with(context)
            .load(data.getUrl_image())
            .into(bgImageView);

        itemView.setOnClickListener(v -> RxBus.send(new LookBookPresenterImpl.RxEventNavigateToDetail(true)));
    }
}
