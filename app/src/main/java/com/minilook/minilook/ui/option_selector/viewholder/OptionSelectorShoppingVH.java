package com.minilook.minilook.ui.option_selector.viewholder;

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
import com.minilook.minilook.data.model.shopping.ShoppingOptionDataModel;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.util.StringUtil;
import java.util.Locale;
import lombok.Setter;

public class OptionSelectorShoppingVH extends BaseViewHolder<ShoppingOptionDataModel> {

    @BindView(R.id.img_product_thumb) ImageView thumbImageView;
    @BindView(R.id.txt_index) TextView indexTextView;
    @BindView(R.id.txt_product_name) TextView nameTextView;
    @BindView(R.id.txt_option) TextView optionTextView;
    @BindView(R.id.txt_count) TextView countTextView;
    @BindView(R.id.txt_price) TextView priceTextView;

    @BindString(R.string.shoppingbag_goods_option) String format_options;
    @BindString(R.string.option_selector_product_index) String format_product_index;
    @BindString(R.string.option_selector_bonus_product_index) String str_bonus_product;

    @BindDrawable(R.drawable.placeholder_image) Drawable placeholder_image;

    @Setter private OnButtonClickListener onButtonClickListener;

    public OptionSelectorShoppingVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_option_selector_shopping, (ViewGroup) itemView, false));
    }

    @Override public void bind(ShoppingOptionDataModel $data) {
        super.bind($data);

        Glide.with(context)
            .load(data.getImage())
            .placeholder(placeholder_image)
            .error(placeholder_image)
            .transition(new DrawableTransitionOptions().crossFade())
            .into(thumbImageView);
        if (data.getProductIndex() == 0) {
            indexTextView.setText(str_bonus_product);
        } else {
            indexTextView.setText(
                String.format(format_product_index, String.format(Locale.KOREA, "%02d", data.getProductIndex())));
        }

        nameTextView.setText(data.getProductName());
        optionTextView.setText(String.format(format_options, data.getColorName(), data.getSizeName()));
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
