package com.minilook.minilook.ui.product_option_selector.viewholder;

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
import com.minilook.minilook.ui.base._BaseViewHolder;
import com.minilook.minilook.util.StringUtil;
import lombok.Setter;

public class ProductOptionSelectorOptionVH extends _BaseViewHolder<ShoppingOptionDataModel> {

    @BindView(R.id.txt_title) TextView titleTextView;
    @BindView(R.id.txt_count) TextView countTextView;
    @BindView(R.id.txt_price) TextView priceTextView;

    @BindString(R.string.shoppingbag_goods_option) String format_options;

    @Setter private OnButtonClickListener onButtonClickListener;

    public ProductOptionSelectorOptionVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_product_option_selector_option, (ViewGroup) itemView, false));
    }

    @Override public void bind(ShoppingOptionDataModel $data) {
        super.bind($data);

        titleTextView.setText(String.format(format_options, data.getColorName(), data.getSizeName()));
        countTextView.setText(String.valueOf(data.getQuantity()));
        priceTextView.setText(StringUtil.toDigit(data.getPriceSum() * data.getQuantity()));
    }

    @OnClick(R.id.img_minus)
    void onMinusClick() {
        if (data.getQuantity() == 1) return;
        data.setQuantity(data.getQuantity() - 1);
        onButtonClickListener.onMinusClick();
    }

    @OnClick(R.id.img_plus)
    void onPlusClick() {
        if (data.getQuantity() >= data.getLimitQuantity()) return;
        data.setQuantity(data.getQuantity() + 1);
        onButtonClickListener.onPlusClick();
    }

    @OnClick(R.id.img_delete)
    void onDeleteClick() {
        onButtonClickListener.onDeleteClick(data);
    }

    public interface OnButtonClickListener {
        void onDeleteClick(ShoppingOptionDataModel data);

        void onMinusClick();

        void onPlusClick();
    }
}
