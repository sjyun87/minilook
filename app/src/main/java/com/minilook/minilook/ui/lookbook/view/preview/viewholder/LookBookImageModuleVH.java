package com.minilook.minilook.ui.lookbook.view.preview.viewholder;

import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import butterknife.BindColor;
import butterknife.BindView;
import com.bumptech.glide.Glide;
import com.minilook.minilook.R;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.lookbook.LookBookPresenterImpl;

public class LookBookImageModuleVH extends BaseViewHolder<String> {

    @BindView(R.id.img_lookbook) ImageView bgImageView;

    @BindColor(R.color.color_FFEEEFF5) int color_FFEEEFF5;

    public LookBookImageModuleVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_lookbook_module_image, (ViewGroup) itemView, false));
    }

    @Override public void bind(String $data) {
        super.bind($data);

        Glide.with(context)
            .load(data)
            .thumbnail(0.3f)
            .placeholder(new ColorDrawable(color_FFEEEFF5))
            .into(bgImageView);

        itemView.setOnClickListener(this::onItemClick);
    }

    void onItemClick(View view) {
        RxBus.send(new LookBookPresenterImpl.RxEventNavigateToDetail(true));
    }
}
