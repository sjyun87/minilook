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
import com.minilook.minilook.data.model.order.OrderOptionDataModel;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.shoppingbag.ShoppingBagPresenterImpl;
import com.minilook.minilook.util.StringUtil;

public class ShoppingBagGoodsItemVH extends BaseViewHolder<OrderOptionDataModel> {

    @BindView(R.id.txt_title) TextView titleTextView;
    @BindView(R.id.txt_count) TextView countTextView;
    @BindView(R.id.txt_price) TextView priceTextView;
    @BindView(R.id.txt_order_available) TextView orderAvailableTextView;

    @BindString(R.string.shoppingbag_goods_option) String format_options;
    @BindString(R.string.shoppingbag_order_available) String format_order_available;

    public ShoppingBagGoodsItemVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_shoppingbag_goods, (ViewGroup) itemView, false));
    }

    @Override public void bind(OrderOptionDataModel $data) {
        super.bind($data);

        titleTextView.setText(String.format(format_options, data.getColor_name(), data.getSize_name()));
        countTextView.setText(String.valueOf(data.getQuantity()));
        priceTextView.setText(StringUtil.toDigit(data.getPrice()));

        if (data.getQuantity() > 0) {
            orderAvailableTextView.setText(String.format(format_order_available, StringUtil.toDigit(data.getOrder_available_quantity())));
            orderAvailableTextView.setVisibility(View.VISIBLE);
        } else {
            orderAvailableTextView.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.img_minus)
    void onMinusClick() {
        if (data.getQuantity() == 1) return;
        data.setQuantity(data.getQuantity() - 1);
        RxBus.send(new ShoppingBagPresenterImpl.RxBusEventSelectedGoodsCount(data));
    }

    @OnClick(R.id.img_plus)
    void onPlusClick() {
        if (data.getQuantity() >= data.getOrder_available_quantity()) return;
        data.setQuantity(data.getQuantity() + 1);
        RxBus.send(new ShoppingBagPresenterImpl.RxBusEventSelectedGoodsCount(data));
    }

    @OnClick(R.id.img_delete)
    void onDeleteClick() {
        RxBus.send(new ShoppingBagPresenterImpl.RxBusEventSelectedGoodsDelete(data));
    }
}
