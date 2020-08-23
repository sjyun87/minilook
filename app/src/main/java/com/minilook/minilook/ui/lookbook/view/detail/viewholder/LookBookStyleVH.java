package com.minilook.minilook.ui.lookbook.view.detail.viewholder;

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
import java.lang.String;
import com.minilook.minilook.ui.base.BaseViewHolder;

public class LookBookStyleVH extends BaseViewHolder<String> {

    @BindView(R.id.img_style) ImageView imageView;

    @BindColor(R.color.color_FFEEEFF5) int color_FFEEEFF5;

    public LookBookStyleVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_lookbook_detail_style, (ViewGroup) itemView, false));
    }

    @Override public void bind(String $data) {
        super.bind($data);

        Glide.with(context)
            .load(data)
            .placeholder(new ColorDrawable(color_FFEEEFF5))
            .into(imageView);

        itemView.setOnClickListener(this::onItemClick);
    }

    void onItemClick(View view) {
        // 갤러리
    }
}
