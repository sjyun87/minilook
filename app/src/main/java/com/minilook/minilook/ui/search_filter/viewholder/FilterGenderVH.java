package com.minilook.minilook.ui.search_filter.viewholder;

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
import butterknife.BindView;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.common.GenderDataModel;
import com.minilook.minilook.ui.base.BaseViewHolder;
import lombok.Setter;

public class FilterGenderVH extends BaseViewHolder<GenderDataModel> {

    @BindView(R.id.txt_gender) TextView textView;

    @BindDrawable(R.drawable.bg_filter_button_off) Drawable bg_button_off;
    @BindDrawable(R.drawable.bg_filter_button_on) Drawable bg_button_on;
    @BindFont(R.font.nanum_square_b) Typeface font_bold;
    @BindFont(R.font.nanum_square_r) Typeface font_regular;
    @BindColor(R.color.color_FF8140E5) int color_FF8140E5;
    @BindColor(R.color.color_FF232323) int color_FF232323;

    @Setter private OnGenderListener listener;

    public FilterGenderVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_filter_gender, (ViewGroup) itemView, false));
    }

    @Override public void bind(GenderDataModel $data) {
        super.bind($data);

        if (data.isSelected()) {
            textView.setBackground(bg_button_on);
            textView.setTypeface(font_bold);
            textView.setTextColor(color_FF8140E5);
        } else {
            textView.setBackground(bg_button_off);
            textView.setTypeface(font_regular);
            textView.setTextColor(color_FF232323);
        }
        textView.setText(data.getName());

        itemView.setOnClickListener(this::onItemClick);
    }

    void onItemClick(View view) {
        if (listener != null) listener.OnGenderSelected(data);
    }

    public interface OnGenderListener {
        void OnGenderSelected(GenderDataModel data);
    }
}
