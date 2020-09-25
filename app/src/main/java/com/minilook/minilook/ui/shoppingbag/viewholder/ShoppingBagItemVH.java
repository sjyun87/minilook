package com.minilook.minilook.ui.shoppingbag.viewholder;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindColor;
import butterknife.BindDimen;
import butterknife.BindDrawable;
import butterknife.BindFont;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.fondesa.recyclerviewdivider.DividerDecoration;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.shopping.ShoppingBrandDataModel;
import com.minilook.minilook.data.code.ShippingCode;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.brand_detail.BrandDetailActivity;
import com.minilook.minilook.ui.shoppingbag.adapter.ShoppingBagProductAdapter;
import com.minilook.minilook.util.DimenUtil;
import com.minilook.minilook.util.SpannableUtil;
import com.minilook.minilook.util.StringUtil;
import jp.wasabeef.glide.transformations.CropCircleWithBorderTransformation;

public class ShoppingBagItemVH extends BaseViewHolder<ShoppingBrandDataModel> {

    @BindView(R.id.img_brand_logo) ImageView logoImageView;
    @BindView(R.id.txt_brand_name) TextView nameTextView;
    @BindView(R.id.rcv_product) RecyclerView recyclerView;
    @BindView(R.id.layout_billing_panel) ConstraintLayout billingPanel;
    @BindView(R.id.txt_shipping_price) TextView shippingPriceTextView;
    @BindView(R.id.txt_free_shipping_condition) TextView freeShippingConditionTextView;
    @BindView(R.id.txt_free_shipping_remain) TextView freeShippingRemainTextView;
    @BindView(R.id.txt_product_price) TextView productPriceTextView;
    @BindView(R.id.txt_final_shipping_price) TextView finalShippingPriceTextView;
    @BindView(R.id.txt_total_price) TextView totalPriceTextView;

    @BindDrawable(R.drawable.placeholder_logo) Drawable img_placeholder_logo;

    @BindColor(R.color.color_FFDBDBDB) int color_FFDBDBDB;
    @BindColor(R.color.color_FF6200EA) int color_FF6200EA;

    @BindFont(R.font.nanum_square_b) Typeface font_bold;

    @BindDimen(R.dimen.dp_2) int dp_2;

    @BindString(R.string.shoppingbag_shipping_free) String str_shipping_free;
    @BindString(R.string.shoppingbag_shipping_free_condition) String format_free_shipping_condition;
    @BindString(R.string.shoppingbag_remain_shipping_free) String format_remain_shipping_free;
    @BindString(R.string.shoppingbag_remain_shipping_free_bold) String str_remain_shipping_free_bold;
    @BindString(R.string.shoppingbag_remain_shipping_free_purple) String str_remain_shipping_free_purple;
    @BindString(R.string.shoppingbag_final_shipping_price) String format_final_shipping_price;

    private ShoppingBagProductAdapter productAdapter;

    public ShoppingBagItemVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_shoppingbag, (ViewGroup) itemView, false));
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        productAdapter = new ShoppingBagProductAdapter();
        recyclerView.setAdapter(productAdapter);
        DividerDecoration.builder(context)
            .size(dp_2)
            .asSpace()
            .build()
            .addTo(recyclerView);
    }

    @Override public void bind(ShoppingBrandDataModel $data) {
        super.bind($data);

        Glide.with(context)
            .load(data.getBrandLogo())
            .placeholder(img_placeholder_logo)
            .error(img_placeholder_logo)
            .apply(RequestOptions.bitmapTransform(
                new CropCircleWithBorderTransformation(DimenUtil.dpToPx(context, 1), color_FFDBDBDB)))
            .transition(new DrawableTransitionOptions().crossFade())
            .into(logoImageView);

        nameTextView.setText(data.getBrandName());

        productAdapter.set(data.getProducts());
        productAdapter.refresh();

        if (data.isBillVisible()) {
            billingPanel.setVisibility(View.VISIBLE);

            if (data.getShippingType() == ShippingCode.CONDITIONAL.getValue()) {
                int freeShippingCondition = data.getConditionFreeShipping() / 10000;
                freeShippingConditionTextView.setText(
                    String.format(format_free_shipping_condition, freeShippingCondition));
                freeShippingConditionTextView.setVisibility(View.VISIBLE);
            } else {
                freeShippingConditionTextView.setVisibility(View.GONE);
            }

            if (data.isFreeShipping()) {
                freeShippingRemainTextView.setVisibility(View.GONE);
                shippingPriceTextView.setText(str_shipping_free);
            } else {
                if (data.getShippingType() == ShippingCode.CONDITIONAL.getValue()) {
                    int remainPrice = data.getConditionFreeShipping() - data.getTotalProductsPrice();
                    freeShippingRemainTextView.setText(getSpanText(remainPrice));
                    freeShippingRemainTextView.setVisibility(View.VISIBLE);
                } else {
                    freeShippingRemainTextView.setVisibility(View.GONE);
                }
                shippingPriceTextView.setText(StringUtil.toDigit(data.getFinalShippingPrice()));
            }

            productPriceTextView.setText(StringUtil.toDigit(data.getTotalProductsPrice()));
            finalShippingPriceTextView.setText(
                String.format(format_final_shipping_price, StringUtil.toDigit(data.getFinalShippingPrice())));

            totalPriceTextView.setText(
                StringUtil.toDigit(data.getTotalProductsPrice() + data.getFinalShippingPrice()));
        } else {
            billingPanel.setVisibility(View.GONE);
        }
    }

    private SpannableString getSpanText(int remainShippingFree) {
        String total = String.format(format_remain_shipping_free, StringUtil.toDigit(remainShippingFree));
        String price = String.format(str_remain_shipping_free_bold, StringUtil.toDigit(remainShippingFree));

        SpannableString fontSpan = SpannableUtil.fontSpan(total, price, font_bold);
        SpannableString colorSpan =
            SpannableUtil.foregroundColorSpan(fontSpan, str_remain_shipping_free_purple, color_FF6200EA);
        return colorSpan;
    }

    @OnClick(R.id.layout_brand_panel)
    void onBrandClick(){
        BrandDetailActivity.start(context, data.getBrandNo());
    }
}
