package com.minilook.minilook.ui.main.fragment.lookbook.view.detail.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.util.DimenUtil;

import butterknife.BindView;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class LookBookImageVH extends BaseViewHolder<String> {

    @BindView(R.id.img_lookbook_detail) ImageView imageView;

    public LookBookImageVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_lookbook_derail_image, (ViewGroup) itemView, false));
    }

    public void bind(String $data, int position) {
        super.bind($data);

        if (position == 0) {
            Glide.with(itemView)
                .load(data)
                .apply(RequestOptions.bitmapTransform(
                    new RoundedCornersTransformation(DimenUtil.dpToPx(context, 50), 0,
                        RoundedCornersTransformation.CornerType.BOTTOM_LEFT)))
                .into(imageView);
        } else {
            Glide.with(itemView)
                .load(data)
                .into(imageView);
        }
    }
}
