package com.minilook.minilook.ui.order.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import butterknife.BindString;
import butterknife.BindView;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.shopping.ShoppingOptionDataModel;
import com.minilook.minilook.ui.base._BaseViewHolder;
import com.minilook.minilook.util.StringUtil;

public class OrderOptionItemVH extends _BaseViewHolder<ShoppingOptionDataModel> {

    @BindView(R.id.txt_title) TextView titleTextView;
    @BindView(R.id.txt_count) TextView countTextView;
    @BindView(R.id.txt_price) TextView priceTextView;

    @BindString(R.string.order_product_option) String format_options;
    @BindString(R.string.order_product_option_count) String format_options_count;

    public OrderOptionItemVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_order_option, (ViewGroup) itemView, false));
    }

    @Override public void bind(ShoppingOptionDataModel $data) {
        super.bind($data);

        titleTextView.setText(String.format(format_options, data.getColorName(), data.getSizeName()));
        countTextView.setText(String.format(format_options_count, StringUtil.toDigit(data.getQuantity())));
        priceTextView.setText(StringUtil.toDigit(data.getPriceSum() * data.getQuantity()));
    }
}
