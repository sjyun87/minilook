package com.minilook.minilook.ui.order_detail.viewholder.status;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.order.OrderGoodsDataModel;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.data.type.OrderStatus;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.order_detail.OrderDetailPresenterImpl;
import com.minilook.minilook.util.StringUtil;

public class DeliveryCompletedVH extends BaseViewHolder<OrderGoodsDataModel> {

    @BindView(R.id.txt_state) TextView stateTextView;
    @BindView(R.id.img_thumb) ImageView thumbImageView;
    @BindView(R.id.txt_name) TextView nameTextView;
    @BindView(R.id.txt_option) TextView optionTextView;
    @BindView(R.id.txt_price) TextView priceTextView;

    @BindString(R.string.order_detail_option) String format_option;

    @BindDrawable(R.drawable.placeholder_image) Drawable placeholder_image;

    public DeliveryCompletedVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_order_type_delivery_completed, (ViewGroup) itemView, false));
    }

    @Override public void bind(OrderGoodsDataModel $data) {
        super.bind($data);

        stateTextView.setText(OrderStatus.toName(data.getStatusCode()));

        Glide.with(context)
            .load(data.getThumbUrl())
            .placeholder(placeholder_image)
            .error(placeholder_image)
            .transition(new DrawableTransitionOptions().crossFade())
            .into(thumbImageView);

        nameTextView.setText(data.getName());
        optionTextView.setText(String.format(format_option, data.getColorName(), data.getSizeName()));
        priceTextView.setText(StringUtil.toDigit(data.getProductPrice()));
    }

    @OnClick(R.id.txt_exchange_n_return)
    void onExchangeNReturnClick() {
        RxBus.send(new OrderDetailPresenterImpl.RxBusEventExchangeNReturnClick(data));
    }

    @OnClick(R.id.txt_delivery_tracking)
    void onDeliveryTrackingClick() {
        RxBus.send(new OrderDetailPresenterImpl.RxBusEventDeliveryTrackingClick(data.getTrackingUrl()));
    }

    @OnClick(R.id.txt_purchase_confirm)
    void onPurchaseConfirmClick() {
        RxBus.send(new OrderDetailPresenterImpl.RxBusEventPurchaseConfirmClick(data.getOrderOptionNo()));
    }

    @OnClick(R.id.txt_question)
    void onQuestionClick() {
        RxBus.send(new OrderDetailPresenterImpl.RxBusEventQuestionClick(data.getCsPhone()));
    }
}
