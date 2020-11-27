package com.minilook.minilook.ui.option_selector.viewholder;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.minilook.minilook.R;
import com.minilook.minilook.data.code.DisplayCode;
import com.minilook.minilook.data.model.product.OptionProductDataModel;
import com.minilook.minilook.ui.base._BaseViewHolder;
import com.minilook.minilook.util.StringUtil;
import java.util.Locale;
import lombok.Setter;

public class OptionSelectorProductVH extends _BaseViewHolder<OptionProductDataModel> {

    @BindView(R.id.txt_index) TextView indexTextView;
    @BindView(R.id.img_product_thumb) ImageView thumbImageView;
    @BindView(R.id.layout_display_curtain) FrameLayout displayCurtainView;
    @BindView(R.id.txt_display_label) TextView displayLabelTextView;
    @BindView(R.id.txt_product_name) TextView nameTextView;
    @BindView(R.id.txt_product_price) TextView priceTextView;

    @BindDrawable(R.drawable.ph_square) Drawable placeholder_image;

    @BindString(R.string.option_selector_product_index) String format_product_index;
    @BindString(R.string.option_selector_bonus_product_index) String str_bonus_product;

    @Setter private OnProductSelectedListener onProductSelectedListener;

    public OptionSelectorProductVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_option_selector_product, (ViewGroup) itemView, false));
    }

    public void bind(OptionProductDataModel $data, int position) {
        super.bind($data);

        Glide.with(context)
            .load(data.getImage())
            .placeholder(placeholder_image)
            .error(placeholder_image)
            .transition(new DrawableTransitionOptions().crossFade())
            .into(thumbImageView);

        nameTextView.setText(data.getProductName());
        priceTextView.setText(StringUtil.toDigit(data.getPrice()));

        if (data.isBonus()) {
            indexTextView.setText(str_bonus_product);
        } else {
            indexTextView.setText(
                String.format(format_product_index, String.format(Locale.KOREA, "%02d", data.getPosition() + 1)));
        }

        if (data.getDisplayCode() == DisplayCode.DISPLAY.getValue()) {
            displayCurtainView.setVisibility(View.GONE);

            itemView.setEnabled(true);
            itemView.setClickable(true);
        } else {
            displayCurtainView.setVisibility(View.VISIBLE);
            displayLabelTextView.setText(data.getDisplayLabel());

            itemView.setEnabled(false);
            itemView.setOnClickListener(null);
        }

        itemView.setOnClickListener(v -> {
            if (onProductSelectedListener != null) onProductSelectedListener.onProductSelected(data);
        });
    }

    public interface OnProductSelectedListener {
        void onProductSelected(OptionProductDataModel data);
    }
}
