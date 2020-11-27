package com.minilook.minilook.ui.guide.viewholder;

import android.graphics.Typeface;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.FontRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.core.content.res.ResourcesCompat;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.minilook.minilook.R;
import com.minilook.minilook.databinding.ItemGuideBinding;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.util.SpannableUtil;

public class GuideVH extends BaseViewHolder<Integer> {

    @StringRes int str_desc1 = R.string.guide_desc1;
    @StringRes int str_desc1_bold = R.string.guide_desc1_b;
    @StringRes int str_desc2 = R.string.guide_desc2;
    @StringRes int str_desc2_bold = R.string.guide_desc2_b;
    @StringRes int str_desc3 = R.string.guide_desc3;
    @StringRes int str_desc3_bold = R.string.guide_desc3_b;
    @StringRes int str_desc4 = R.string.guide_desc4;
    @StringRes int str_desc4_bold = R.string.guide_desc4_b;

    @DrawableRes int img_guide1 = R.drawable.img_guide1;
    @DrawableRes int img_guide2 = R.drawable.img_guide2;
    @DrawableRes int img_guide3 = R.drawable.img_guide3;
    @DrawableRes int img_guide4 = R.drawable.img_guide4;
    @DrawableRes int ph_square = R.drawable.ph_square;

    @ColorRes int color_FF8140E5 = R.color.color_FF8140E5;

    @FontRes int font_bold = R.font.nanum_square_b;

    private final ItemGuideBinding binding;

    public GuideVH(@NonNull View parent) {
        super(ItemGuideBinding.inflate(LayoutInflater.from(parent.getContext()), (ViewGroup) parent, false));
        binding = ItemGuideBinding.bind(itemView);
    }

    @Override public void bind(Integer $data) {
        super.bind($data);

        Glide.with(context)
            .load(getGuideImage(data))
            .placeholder(ph_square)
            .error(ph_square)
            .transition(new DrawableTransitionOptions().crossFade())
            .into(binding.imgGuide);
        binding.txtDesc.setText(getDescText(data));
    }

    private int getGuideImage(int position) {
        switch (position) {
            default:
            case 0:
                return img_guide1;
            case 1:
                return img_guide2;
            case 2:
                return img_guide3;
            case 3:
                return img_guide4;
        }
    }

    private SpannableString getDescText(int position) {
        switch (position) {
            default:
            case 0:
                return getSpan(str_desc1, str_desc1_bold);
            case 1:
                return getSpan(str_desc2, str_desc2_bold);
            case 2:
                return getSpan(str_desc3, str_desc3_bold);
            case 3:
                return getSpan(str_desc4, str_desc4_bold);
        }
    }

    private SpannableString getSpan(int textId, int targetId) {
        Typeface font = ResourcesCompat.getFont(context, font_bold);
        String text = resources.getString(textId);
        String target = resources.getString(targetId);

        SpannableString fontSpan = SpannableUtil.fontSpan(text, target, font);
        SpannableString colorSpan = SpannableUtil.foregroundColorSpan(fontSpan, target, color_FF8140E5);
        return colorSpan;
    }
}
