package com.minilook.minilook.ui.product_option_selector.viewholder;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import butterknife.BindColor;
import butterknife.BindFont;
import butterknife.BindString;
import butterknife.BindView;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.product.OptionSizeDataModel;
import com.minilook.minilook.ui.base._BaseViewHolder;
import com.minilook.minilook.util.StringUtil;
import lombok.Setter;

public class ProductOptionSelectorSizeVH extends _BaseViewHolder<OptionSizeDataModel> {

    @BindView(R.id.txt_size) TextView sizeTextView;
    @BindView(R.id.txt_price_add) TextView addPriceTextView;
    @BindView(R.id.txt_size_sold_out) TextView soldOutTextView;

    @BindColor(R.color.color_FF424242) int color_FF424242;
    @BindColor(R.color.color_FFA9A9A9) int color_FFA9A9A9;

    @BindFont(R.font.nanum_square_b) Typeface font_bold;
    @BindFont(R.font.nanum_square_r) Typeface font_regular;

    @BindString(R.string.option_selector_price_add) String format_price_add;

    @Setter private OnSizeSelectedListener onSizeSelectedListener;

    public ProductOptionSelectorSizeVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_product_option_selector_size, (ViewGroup) itemView, false));
    }

    @Override public void bind(OptionSizeDataModel $data) {
        super.bind($data);

        sizeTextView.setText(data.getSizeName());
        if (data.getPriceAdd() == 0) {
            addPriceTextView.setVisibility(View.GONE);
        } else {
            addPriceTextView.setText(String.format(format_price_add, StringUtil.toDigit(data.getPriceAdd())));
            addPriceTextView.setVisibility(View.VISIBLE);
        }

        if (data.getSizeStock() == 0) {
            soldOutTextView.setVisibility(View.VISIBLE);
            sizeTextView.setPaintFlags(sizeTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            sizeTextView.setTypeface(font_regular);
            sizeTextView.setTextColor(color_FFA9A9A9);
            itemView.setEnabled(false);
        } else {
            soldOutTextView.setVisibility(View.INVISIBLE);
            sizeTextView.setPaintFlags(0);
            sizeTextView.setTypeface(font_bold);
            sizeTextView.setTextColor(color_FF424242);
            itemView.setEnabled(true);
        }

        itemView.setOnClickListener(v -> {
            if (onSizeSelectedListener != null) onSizeSelectedListener.onSizeSelected(data);
        });
    }

    public interface OnSizeSelectedListener {
        void onSizeSelected(OptionSizeDataModel data);
    }
}
