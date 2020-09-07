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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindColor;
import butterknife.BindDimen;
import butterknife.BindDrawable;
import butterknife.BindFont;
import butterknife.BindString;
import butterknife.BindView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.fondesa.recyclerviewdivider.DividerDecoration;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.order.ShoppingBagDataModel;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.shoppingbag.adapter.ShoppingBagProductAdapter;
import com.minilook.minilook.util.DimenUtil;
import com.minilook.minilook.util.SpannableUtil;
import com.minilook.minilook.util.StringUtil;
import jp.wasabeef.glide.transformations.CropCircleWithBorderTransformation;

public class ShoppingBagItemVH extends BaseViewHolder<ShoppingBagDataModel> {

    @BindView(R.id.img_brand_logo) ImageView logoImageView;
    @BindView(R.id.txt_brand_name) TextView nameTextView;
    @BindView(R.id.rcv_product) RecyclerView recyclerView;
    @BindView(R.id.txt_shipping_price) TextView shippingPriceTextView;
    @BindView(R.id.txt_shipping_limit) TextView shippingLimitTextView;
    @BindView(R.id.txt_remain_shipping_free) TextView remainShippingFreeTextView;
    @BindView(R.id.txt_product_price) TextView productPriceTextView;
    @BindView(R.id.txt_final_shipping_price) TextView finalShippingPriceTextView;
    @BindView(R.id.txt_total_price) TextView totalPriceTextView;

    @BindDrawable(R.drawable.placeholder_logo) Drawable img_placeholder_logo;

    @BindColor(R.color.color_FFDBDBDB) int color_FFDBDBDB;
    @BindColor(R.color.color_FF6200EA) int color_FF6200EA;

    @BindFont(R.font.nanum_square_b) Typeface font_bold;

    @BindDimen(R.dimen.dp_2) int dp_2;

    @BindString(R.string.shoppingbag_shipping_free) String str_shipping_free;
    @BindString(R.string.shoppingbag_shipping_free_condition) String format_shipping_limit;
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

    @Override public void bind(ShoppingBagDataModel $data) {
        super.bind($data);

        Glide.with(context)
            .load(data.getBrand_logo())
            .placeholder(img_placeholder_logo)
            .error(img_placeholder_logo)
            .apply(RequestOptions.bitmapTransform(
                new CropCircleWithBorderTransformation(DimenUtil.dpToPx(context, 1), color_FFDBDBDB)))
            .transition(new DrawableTransitionOptions().crossFade())
            .into(logoImageView);

        nameTextView.setText(data.getBrand_name());

        productAdapter.set(data.getProducts());
        productAdapter.refresh();

        shippingLimitTextView.setText(String.format(format_shipping_limit, (data.getShipping_limit() / 10000)));
        String shippingPrice = data.isShippingFree() ? str_shipping_free : StringUtil.toDigit(data.getShipping_price());
        shippingPriceTextView.setText(shippingPrice);

        if (data.isShippingFree()) {
            remainShippingFreeTextView.setVisibility(View.GONE);
        } else {
            remainShippingFreeTextView.setText(getSpanText(data.getRemainShippingFree()));
            remainShippingFreeTextView.setVisibility(View.VISIBLE);
        }

        productPriceTextView.setText(StringUtil.toDigit(data.getProductsPrice()));

        int finalShippingPrice = data.isShippingFree() ? 0 : data.getShipping_price();
        finalShippingPriceTextView.setText(String.format(format_final_shipping_price, StringUtil.toDigit(finalShippingPrice)));

        totalPriceTextView.setText(StringUtil.toDigit(data.getProductsPrice() + finalShippingPrice));
    }

    private SpannableString getSpanText(int remainShippingFree) {
        String total = String.format(format_remain_shipping_free, StringUtil.toDigit(remainShippingFree));
        String price = String.format(str_remain_shipping_free_bold, StringUtil.toDigit(remainShippingFree));

        SpannableString fontSpan = SpannableUtil.fontSpan(total, price, font_bold);
        SpannableString colorSpan = SpannableUtil.foregroundColorSpan(fontSpan, str_remain_shipping_free_purple, color_FF6200EA);
        return colorSpan;
    }
}
