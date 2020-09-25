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
import com.minilook.minilook.App;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.data.rx.RxBusEvent;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.login.LoginActivity;
import com.minilook.minilook.ui.product_detail.ProductDetailActivity;
import com.minilook.minilook.util.StringUtil;
import lombok.Setter;

public class ProductSize84VH extends BaseViewHolder<ProductDataModel> {

    @BindView(R.id.img_product_thumb) ImageView thumbImageView;
    @BindView(R.id.img_scrap) ImageView scrapImageView;
    @BindView(R.id.txt_brand_name) TextView brandNameTextView;
    @BindView(R.id.txt_product_name) TextView productNameTextView;
    @BindView(R.id.txt_discount_percent) TextView discountPercentTextView;
    @BindView(R.id.txt_price) TextView priceTextView;

    @BindString(R.string.base_price_percent) String format_percent;

    @BindDrawable(R.drawable.placeholder_image) Drawable img_placeholder;
    @BindDrawable(R.drawable.ic_scrap_off) Drawable img_scrap_off;
    @BindDrawable(R.drawable.ic_scrap_on) Drawable img_scrap_on;

    @Setter private boolean isShowScrap;
    @Setter private boolean isShowBrand;

    public ProductSize84VH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_product_type_size_84, (ViewGroup) itemView, false));
    }

    @Override public void bind(ProductDataModel $data) {
        super.bind($data);

        if (isShowScrap) {
            showScrap();
        } else {
            hideScrap();
        }

        Glide.with(context)
            .load(data.getImageUrl())
            .placeholder(img_placeholder)
            .error(img_placeholder)
            .transition(new DrawableTransitionOptions().crossFade())
            .into(thumbImageView);

        brandNameTextView.setText(data.getBrandName());
        productNameTextView.setText(data.getProductName());

        if (data.isDiscount()) {
            discountPercentTextView.setText(String.format(format_percent, data.getDiscountPercent()));
            discountPercentTextView.setVisibility(View.VISIBLE);
        } else {
            discountPercentTextView.setVisibility(View.GONE);
        }
        priceTextView.setText(StringUtil.toDigit(data.getPrice()));

        setupScrapImage();

        itemView.setOnClickListener(this::onItemClick);
    }

    private void setupScrapImage() {
        if (data.isScrap()) {
            scrapImageView.setImageDrawable(img_scrap_on);
        } else {
            scrapImageView.setImageDrawable(img_scrap_off);
        }
    }

    public void showScrap() {
        scrapImageView.setVisibility(View.VISIBLE);
    }

    public void hideScrap() {
        scrapImageView.setVisibility(View.GONE);
    }

    void onItemClick(View view) {
        ProductDetailActivity.start(context, data.getProductNo());
    }

    @OnClick(R.id.img_scrap)
    void onScrapClick() {
        if (App.getInstance().isLogin()) {
            data.setScrap(!data.isScrap());
            setupScrapImage();
            RxBus.send(new RxBusEvent.RxBusEventProductScrap(data.isScrap(), data));
        } else {
            LoginActivity.start(context);
        }
    }
}
