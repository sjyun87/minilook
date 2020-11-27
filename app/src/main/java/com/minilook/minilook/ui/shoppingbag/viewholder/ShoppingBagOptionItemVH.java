package com.minilook.minilook.ui.shoppingbag.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.shopping.ShoppingOptionDataModel;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.ui.base._BaseViewHolder;
import com.minilook.minilook.ui.shoppingbag.ShoppingBagPresenterImpl;
import com.minilook.minilook.util.StringUtil;

public class ShoppingBagOptionItemVH extends _BaseViewHolder<ShoppingOptionDataModel> {

    @BindView(R.id.txt_title) TextView titleTextView;
    @BindView(R.id.txt_count) TextView countTextView;
    @BindView(R.id.txt_price) TextView priceTextView;
    @BindView(R.id.txt_order_available) TextView orderAvailableTextView;

    @BindString(R.string.shoppingbag_goods_option) String format_options;
    @BindString(R.string.shoppingbag_order_available) String format_order_available;

    public ShoppingBagOptionItemVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_shoppingbag_option, (ViewGroup) itemView, false));
    }

    @Override public void bind(ShoppingOptionDataModel $data) {
        super.bind($data);

        titleTextView.setText(String.format(format_options, data.getColorName(), data.getSizeName()));
        countTextView.setText(String.valueOf(data.getQuantity()));
        priceTextView.setText(StringUtil.toDigit(data.getPriceSum() * data.getQuantity()));

        if (data.getQuantity() > 0) {
            orderAvailableTextView.setText(String.format(format_order_available, StringUtil.toDigit(data.getLimitQuantity())));
            orderAvailableTextView.setVisibility(View.VISIBLE);
        } else {
            orderAvailableTextView.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.img_minus)
    void onMinusClick() {
        if (data.getQuantity() == 1) return;
        data.setQuantity(data.getQuantity() - 1);
        RxBus.send(new ShoppingBagPresenterImpl.RxBusEventOptionCountChanged(data));
    }

    @OnClick(R.id.img_plus)
    void onPlusClick() {
        if (data.getQuantity() >= data.getLimitQuantity()) return;
        data.setQuantity(data.getQuantity() + 1);
        RxBus.send(new ShoppingBagPresenterImpl.RxBusEventOptionCountChanged(data));
    }

    @OnClick(R.id.img_delete)
    void onDeleteClick() {
        RxBus.send(new ShoppingBagPresenterImpl.RxBusEventOptionDeleted(data));
    }
}
