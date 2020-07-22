package com.minilook.minilook.ui.main.fragment.lookbook.view.detail.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import butterknife.BindView;
import com.bumptech.glide.Glide;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseViewHolder;

public class LookBookStyleVH extends BaseViewHolder<String> {

    @BindView(R.id.img_style) ImageView imageView;

    public LookBookStyleVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_lookbook_detail_style, (ViewGroup) itemView, false));
    }

    @Override public void bind(String $data) {
        super.bind($data);

        Glide.with(itemView)
            .load(data)
            .into(imageView);
    }
}
