package com.minilook.minilook.ui.preorder_detail.viewholder;

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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.minilook.minilook.R;
import com.minilook.minilook.data.code.DisplayCode;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.ui.base._BaseViewHolder;
import com.minilook.minilook.util.StringUtil;
import java.util.Locale;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class PreorderDetailProductVH extends _BaseViewHolder<ProductDataModel> {

    @BindView(R.id.img_product_thumb) ImageView thumbImageView;
    @BindView(R.id.img_curtain) View curtain;
    @BindView(R.id.txt_display_label) TextView displayLabelTextView;
    @BindView(R.id.txt_product_index) TextView indexTextView;
    @BindView(R.id.txt_product_name) TextView productNameTextView;
    @BindView(R.id.txt_discount_percent) TextView discountPercentTextView;
    @BindView(R.id.txt_price) TextView priceTextView;

    @BindString(R.string.base_percent) String format_percent;
    @BindString(R.string.preorder_detail_product_detail_index) String format_index;

    @BindDrawable(R.drawable.ph_square) Drawable img_placeholder;

    public PreorderDetailProductVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_preorder_detail_product, (ViewGroup) itemView, false));
    }

    public void bind(ProductDataModel $data, int position) {
        super.bind($data);

        Glide.with(context)
            .load(data.getImageUrl())
            .placeholder(img_placeholder)
            .error(img_placeholder)
            .transition(new DrawableTransitionOptions().crossFade())
            .into(thumbImageView);

        indexTextView.setText(String.format(format_index, String.format(Locale.getDefault(), "%02d", position + 1)));

        productNameTextView.setText(data.getProductName());

        if (data.getDisplayCode() == DisplayCode.DISPLAY.getValue()) {
            hideCurtain();
            hideDisplayLabel();
        } else {
            showCurtain();
            showDisplayLabel();
            displayLabelTextView.setText(data.getDisplayLabel());
        }

        if (data.isDiscount()) {
            discountPercentTextView.setText(String.format(format_percent, data.getDiscountPercent()));
            discountPercentTextView.setVisibility(View.VISIBLE);
        } else {
            discountPercentTextView.setVisibility(View.GONE);
        }
        priceTextView.setText(StringUtil.toDigit(data.getPrice()));

        itemView.setOnClickListener(this::onItemClick);
    }

    private void showDisplayLabel() {
        displayLabelTextView.setVisibility(View.VISIBLE);
    }

    private void hideDisplayLabel() {
        displayLabelTextView.setVisibility(View.GONE);
    }

    private void showCurtain() {
        curtain.setVisibility(View.VISIBLE);
    }

    private void hideCurtain() {
        curtain.setVisibility(View.GONE);
    }

    void onItemClick(View view) {
        RxBus.send(new RxEventPreorderProductClick(indexTextView.getText().toString(), data.getProductNo()));
    }

    @AllArgsConstructor @Getter public final static class RxEventPreorderProductClick {
        private String title;
        private int productNo;
    }
}
