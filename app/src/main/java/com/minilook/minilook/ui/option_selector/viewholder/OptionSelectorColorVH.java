package com.minilook.minilook.ui.option_selector.viewholder;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import butterknife.BindColor;
import butterknife.BindFont;
import butterknife.BindView;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.product.OptionColorDataModel;
import com.minilook.minilook.ui.base._BaseViewHolder;
import com.minilook.minilook.ui.base.widget.ColorChip;
import lombok.Setter;

public class OptionSelectorColorVH extends _BaseViewHolder<OptionColorDataModel> {

    @BindView(R.id.view_color) ColorChip colorChip;
    @BindView(R.id.txt_color) TextView colorTextView;
    @BindView(R.id.txt_color_sold_out) TextView soldOutTextView;

    @BindColor(R.color.color_FF424242) int color_FF424242;
    @BindColor(R.color.color_FFA9A9A9) int color_FFA9A9A9;

    @BindFont(R.font.nanum_square_b) Typeface font_bold;
    @BindFont(R.font.nanum_square_r) Typeface font_regular;

    @Setter private OnColorSelectedListener onColorSelectedListener;

    public OptionSelectorColorVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_option_selector_color, (ViewGroup) itemView, false));
    }

    @Override public void bind(OptionColorDataModel $data) {
        super.bind($data);

        colorChip.setColor(data.getColorCode());
        colorTextView.setText(data.getColorName());

        if (data.getColorStock() == 0) {
            soldOutTextView.setVisibility(View.VISIBLE);
            colorTextView.setPaintFlags(colorTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            colorTextView.setTypeface(font_regular);
            colorTextView.setTextColor(color_FFA9A9A9);
            itemView.setEnabled(false);
            itemView.setOnClickListener(null);
        } else {
            soldOutTextView.setVisibility(View.INVISIBLE);
            colorTextView.setPaintFlags(0);
            colorTextView.setTypeface(font_bold);
            colorTextView.setTextColor(color_FF424242);
            itemView.setEnabled(true);
            itemView.setClickable(true);
        }

        itemView.setOnClickListener(v -> {
            if (onColorSelectedListener != null) onColorSelectedListener.onColorSelected(data);
        });
    }

    public interface OnColorSelectedListener {
        void onColorSelected(OptionColorDataModel data);
    }
}
