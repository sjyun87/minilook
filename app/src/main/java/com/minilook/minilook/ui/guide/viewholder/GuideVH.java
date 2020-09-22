package com.minilook.minilook.ui.guide.viewholder;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindFont;
import butterknife.BindString;
import butterknife.BindView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.util.SpannableUtil;

public class GuideVH extends BaseViewHolder<Integer> {

    @BindView(R.id.img_guide) ImageView guideImageView;
    @BindView(R.id.txt_desc) TextView descTextView;

    @BindString(R.string.guide_desc1) String str_desc1;
    @BindString(R.string.guide_desc1_b) String str_desc1_bold;
    @BindString(R.string.guide_desc2) String str_desc2;
    @BindString(R.string.guide_desc2_b) String str_desc2_bold;
    @BindString(R.string.guide_desc3) String str_desc3;
    @BindString(R.string.guide_desc3_b) String str_desc3_bold;
    @BindString(R.string.guide_desc4) String str_desc4;
    @BindString(R.string.guide_desc4_b) String str_desc4_bold;

    @BindDrawable(R.drawable.img_guide1) Drawable img_guide1;
    @BindDrawable(R.drawable.img_guide2) Drawable img_guide2;
    @BindDrawable(R.drawable.img_guide3) Drawable img_guide3;
    @BindDrawable(R.drawable.img_guide4) Drawable img_guide4;
    @BindDrawable(R.drawable.placeholder_image) Drawable img_placeholder;

    @BindFont(R.font.nanum_square_b) Typeface font_bold;

    @BindColor(R.color.color_FF8140E5) int color_FF8140E5;

    public GuideVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_guide, (ViewGroup) itemView, false));
    }

    @Override public void bind(Integer $data) {
        super.bind($data);
        switch (data) {
            case 0:
                Glide.with(context)
                    .load(img_guide1)
                    .placeholder(img_placeholder)
                    .error(img_placeholder)
                    .transition(new DrawableTransitionOptions().crossFade())
                    .into(guideImageView);
                descTextView.setText(parseToSpan(str_desc1, str_desc1_bold));
                break;
            case 1:
                Glide.with(context)
                    .load(img_guide2)
                    .placeholder(img_placeholder)
                    .error(img_placeholder)
                    .transition(new DrawableTransitionOptions().crossFade())
                    .into(guideImageView);
                descTextView.setText(parseToSpan(str_desc2, str_desc2_bold));
                break;
            case 2:
                Glide.with(context)
                    .load(img_guide3)
                    .placeholder(img_placeholder)
                    .error(img_placeholder)
                    .transition(new DrawableTransitionOptions().crossFade())
                    .into(guideImageView);
                descTextView.setText(parseToSpan(str_desc3, str_desc3_bold));
                break;
            case 3:
                Glide.with(context)
                    .load(img_guide4)
                    .placeholder(img_placeholder)
                    .error(img_placeholder)
                    .transition(new DrawableTransitionOptions().crossFade())
                    .into(guideImageView);
                descTextView.setText(parseToSpan(str_desc4, str_desc4_bold));
                break;
        }
    }

    private SpannableString parseToSpan(String text, String target) {
        SpannableString fontSpan = SpannableUtil.fontSpan(text, target, font_bold);
        SpannableString colorSpan = SpannableUtil.foregroundColorSpan(fontSpan, target, color_FF8140E5);
        return colorSpan;
    }
}
