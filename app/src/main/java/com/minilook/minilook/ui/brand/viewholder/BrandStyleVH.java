package com.minilook.minilook.ui.brand.viewholder;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindFont;
import butterknife.BindString;
import butterknife.BindView;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.common.CodeDataModel;
import com.minilook.minilook.ui.base._BaseViewHolder;
import lombok.Setter;

public class BrandStyleVH extends _BaseViewHolder<CodeDataModel> {

    @BindView(R.id.txt_style) TextView styleTextView;

    @BindDrawable(R.drawable.bg_brand_style_off) Drawable img_style_off;
    @BindDrawable(R.drawable.bg_brand_style_on) Drawable img_style_on;

    @BindColor(R.color.color_FF8140E5) int color_FF8140E5;
    @BindColor(R.color.color_FF232323) int color_FF232323;

    @BindFont(R.font.nanum_square_r) Typeface font_regular;
    @BindFont(R.font.nanum_square_b) Typeface font_bold;

    @BindString(R.string.base_tag) String format_tag;

    @Setter private OnStyleClickListener onStyleClickListener;

    public BrandStyleVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_brand_style, (ViewGroup) itemView, false));
    }

    @Override public void bind(CodeDataModel $data) {
        super.bind($data);

        styleTextView.setText(String.format(format_tag, data.getName()));

        if (data.isSelected()) {
            styleTextView.setBackground(img_style_on);
            styleTextView.setTextColor(color_FF8140E5);
            styleTextView.setTypeface(font_bold);
        } else {
            styleTextView.setBackground(img_style_off);
            styleTextView.setTextColor(color_FF232323);
            styleTextView.setTypeface(font_regular);
        }

        itemView.setOnClickListener(this::onItemClick);
    }

    void onItemClick(View view) {
        if (onStyleClickListener != null) onStyleClickListener.onStyleClick(data.getPosition());
    }

    public interface OnStyleClickListener {
        void onStyleClick(int position);
    }
}
