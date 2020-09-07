package com.minilook.minilook.ui.option_selector.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.product.GoodsDataModel;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.util.StringUtil;
import lombok.Setter;

public class OptionSelectorGoodsItemVH extends BaseViewHolder<GoodsDataModel> {

    @BindView(R.id.txt_title) TextView titleTextView;
    @BindView(R.id.txt_count) TextView countTextView;
    @BindView(R.id.txt_price) TextView priceTextView;

    @BindString(R.string.shoppingbag_goods_option) String format_options;

    @Setter private OnButtonClickListener onButtonClickListener;

    public OptionSelectorGoodsItemVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_option_selector_goods, (ViewGroup) itemView, false));
    }

    @Override public void bind(GoodsDataModel $data) {
        super.bind($data);

        titleTextView.setText(String.format(format_options, data.getColor_name(), data.getSize_name()));
        countTextView.setText(String.valueOf(data.getSelected_quantity()));
        priceTextView.setText(StringUtil.toDigit(data.getPrice()));
    }

    @OnClick(R.id.img_minus)
    void onMinusClick() {
        if (data.getSelected_quantity() == 1) return;
        data.setSelected_quantity(data.getSelected_quantity() - 1);
        onButtonClickListener.onMinusClick();
    }

    @OnClick(R.id.img_plus)
    void onPlusClick() {
        if (data.getSelected_quantity() >= data.getOrder_available_quantity()) return;
        data.setSelected_quantity(data.getSelected_quantity() + 1);
        onButtonClickListener.onPlusClick();
    }

    @OnClick(R.id.img_delete)
    void onDeleteClick() {
        onButtonClickListener.onDeleteClick(data);
    }

    public interface OnButtonClickListener {
        void onDeleteClick(GoodsDataModel data);

        void onMinusClick();

        void onPlusClick();
    }
}
