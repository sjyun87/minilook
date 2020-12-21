package com.minilook.minilook.ui.search_filter.viewholder;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
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
import com.minilook.minilook.ui.base._BaseViewHolder;
import lombok.Setter;

public class FilterColorVH extends _BaseViewHolder<ColorDataModel> {

    private static final String COLOR_SILVER = "#C0C0C0";
    private static final String COLOR_GOLD = "#FFD700";
    private static final String COLOR_MULTI = "multi";

    @BindView(R.id.img_icon) ImageView iconImageView;
    @BindView(R.id.img_check) ImageView checkImageView;
    @BindView(R.id.txt_name) TextView nameTextView;

    @BindFont(R.font.nanum_square_b) Typeface font_bold;
    @BindFont(R.font.nanum_square_r) Typeface font_regular;
    @BindColor(R.color.color_FF8140E5) int color_FF8140E5;
    @BindColor(R.color.color_FF232323) int color_FF232323;
    @BindDrawable(R.drawable.color_silver_square) Drawable color_silver;
    @BindDrawable(R.drawable.color_gold_square) Drawable color_gold;
    @BindDrawable(R.drawable.color_multi_square) Drawable color_multi;

    @Setter private OnColorListener listener;

    public FilterColorVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_filter_color, (ViewGroup) itemView, false));
    }

    @Override public void bind(ColorDataModel $data) {
        super.bind($data);

        if (data.isSelected()) {
            checkImageView.setVisibility(View.VISIBLE);
            nameTextView.setTextColor(color_FF8140E5);
            nameTextView.setTypeface(font_bold);
        } else {
            checkImageView.setVisibility(View.GONE);
            nameTextView.setTextColor(color_FF232323);
            nameTextView.setTypeface(font_regular);
        }

        switch (data.getCode()) {
            case COLOR_SILVER:
                iconImageView.setImageDrawable(color_silver);
                break;
            case COLOR_GOLD:
                iconImageView.setImageDrawable(color_gold);
                break;
            case COLOR_MULTI:
                iconImageView.setImageDrawable(color_multi);
                break;
            default:
                iconImageView.setImageDrawable(new ColorDrawable(Color.parseColor(data.getCode())));
                break;
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
