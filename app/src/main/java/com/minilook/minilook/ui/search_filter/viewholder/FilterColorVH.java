package com.minilook.minilook.ui.search_filter.viewholder;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindFont;
import butterknife.BindView;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.common.ColorDataModel;
import com.minilook.minilook.ui.base.BaseViewHolder;
import lombok.Setter;

public class FilterColorVH extends BaseViewHolder<ColorDataModel> {

    @BindView(R.id.img_icon) ImageView iconImageView;
    @BindView(R.id.txt_name) TextView nameTextView;

    @BindFont(R.font.nanum_square_b) Typeface font_bold;
    @BindFont(R.font.nanum_square_r) Typeface font_regular;
    @BindColor(R.color.color_FF8140E5) int color_FF8140E5;
    @BindColor(R.color.color_FF232323) int color_FF232323;
    @BindDrawable(R.drawable.color_silver) Drawable color_silver;
    @BindDrawable(R.drawable.color_gold) Drawable color_gold;
    @BindDrawable(R.drawable.color_multi) Drawable color_multi;

    //@BindDrawable(R.drawable.bg_filter_category_off) Drawable bg_icon_off;
    //@BindDrawable(R.drawable.bg_filter_category_on) Drawable bg_icon_on;

    @Setter private OnColorListener listener;

    public FilterColorVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_filter_color, (ViewGroup) itemView, false));
    }

    @Override public void bind(ColorDataModel $data) {
        super.bind($data);

        if (data.isSelected()) {
            //iconImageView.setBackground(bg_icon_on);
            nameTextView.setTextColor(color_FF8140E5);
            nameTextView.setTypeface(font_bold);
        } else {
            //iconImageView.setBackground(bg_icon_off);
            nameTextView.setTextColor(color_FF232323);
            nameTextView.setTypeface(font_regular);
        }

        if (data.getCode().equals("#C0C0C0")) {
            iconImageView.setImageDrawable(color_silver);
        } else if (data.getCode().equals("#FFD700")) {
            iconImageView.setImageDrawable(color_gold);
        } else if (data.getCode().equals("multi")) {
            iconImageView.setImageDrawable(color_multi);
        } else {
            iconImageView.setBackgroundColor(Color.parseColor(data.getCode()));
        }
        nameTextView.setText(data.getName());

        itemView.setOnClickListener(this::onItemClick);
    }

    void onItemClick(View view) {
        if (listener != null) listener.OnColorSelected(data);
    }

    public interface OnColorListener {
        void OnColorSelected(ColorDataModel data);
    }
}
