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
import com.minilook.minilook.data.model.product.ProductStockDataModel;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.util.StringUtil;
import lombok.Setter;

public class OptionSelectorSizeVH extends BaseViewHolder<ProductStockDataModel> {

    @BindView(R.id.txt_size) TextView sizeTextView;
    @BindView(R.id.txt_price_add) TextView addPriceTextView;
    @BindView(R.id.txt_size_sold_out) TextView soldOutTextView;

    @BindColor(R.color.color_FF424242) int color_FF424242;
    @BindColor(R.color.color_FFA9A9A9) int color_FFA9A9A9;

    @BindFont(R.font.nanum_square_b) Typeface font_bold;
    @BindFont(R.font.nanum_square_r) Typeface font_regular;

    @Setter private OnSizeSelectedListener onSizeSelectedListener;

    public OptionSelectorSizeVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_option_selector_size, (ViewGroup) itemView, false));
    }

    @Override public void bind(ProductStockDataModel $data) {
        super.bind($data);

        sizeTextView.setText(data.getSize_name());
        if (data.getPrice_add() == 0) {
            addPriceTextView.setVisibility(View.GONE);
        } else {
            addPriceTextView.setText(StringUtil.toDigit(data.getPrice_add()));
            addPriceTextView.setVisibility(View.VISIBLE);
        }

        if (data.getSize_stock() == 0) {
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
        void onSizeSelected(ProductStockDataModel data);
    }
}
