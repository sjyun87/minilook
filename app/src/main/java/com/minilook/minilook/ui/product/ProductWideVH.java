package com.minilook.minilook.ui.product;

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
import com.minilook.minilook.data.code.DisplayCode;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.ui.base._BaseViewHolder;
import com.minilook.minilook.ui.preorder_detail.PreorderDetailActivity;
import com.minilook.minilook.ui.product_detail.ProductDetailActivity;
import com.minilook.minilook.util.StringUtil;
import lombok.Setter;

public class ProductWideVH extends _BaseViewHolder<ProductDataModel> {

    @BindView(R.id.img_product_thumb) ImageView thumbImageView;
    @BindView(R.id.txt_brand_name) TextView brandNameTextView;
    @BindView(R.id.txt_product_name) TextView productNameTextView;
    @BindView(R.id.txt_discount_percent) TextView discountPercentTextView;
    @BindView(R.id.txt_price) TextView priceTextView;
    @BindView(R.id.curtain) View curtainView;
    @BindView(R.id.txt_display_label) TextView displayLabelTextView;

    @BindString(R.string.base_percent) String format_percent;

    @BindDrawable(R.drawable.ph_square) Drawable img_placeholder;

    @Setter private OnDeleteClickListener listener;

    public ProductWideVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_product_type_wide, (ViewGroup) itemView, false));
    }

    @Override public void bind(ProductDataModel $data) {
        super.bind($data);

        Glide.with(context)
            .load(data.getImageUrl())
            .placeholder(img_placeholder)
            .error(img_placeholder)
            .transition(new DrawableTransitionOptions().crossFade())
            .into(thumbImageView);

        if (data.getDisplayCode() == DisplayCode.DISPLAY.getValue()) {
            hideCurtain();
            hideDisplayLabel();
        } else {
            showCurtain();
            showDisplayLabel();
            displayLabelTextView.setText(data.getDisplayLabel());
        }

        brandNameTextView.setText(data.getBrandName());
        productNameTextView.setText(data.getProductName());

        if (data.isDiscount()) {
            discountPercentTextView.setText(String.format(format_percent, data.getDiscountPercent()));
            discountPercentTextView.setVisibility(View.VISIBLE);
        } else {
            discountPercentTextView.setVisibility(View.GONE);
        }
        priceTextView.setText(StringUtil.toDigit(data.getPrice()));

        itemView.setOnClickListener(this::onItemClick);
    }

    private void showCurtain() {
        curtainView.setVisibility(View.VISIBLE);
    }

    private void hideCurtain() {
        curtainView.setVisibility(View.GONE);
    }

    private void showDisplayLabel() {
        displayLabelTextView.setVisibility(View.VISIBLE);
    }

    private void hideDisplayLabel() {
        displayLabelTextView.setVisibility(View.GONE);
    }

    void onItemClick(View view) {
        if (data.isPreorder()) {
            if (data.getProductNo() > 0) PreorderDetailActivity.start(context, data.getPreorderNo());
        } else {
            ProductDetailActivity.start(context, data.getProductNo());
        }
    }

    @OnClick(R.id.img_delete)
    void onDeleteClick() {
        if (listener != null) listener.onDeleteClick(data);
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(ProductDataModel data);
    }
}
