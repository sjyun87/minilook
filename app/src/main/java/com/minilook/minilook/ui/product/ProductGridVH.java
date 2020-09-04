package com.minilook.minilook.ui.product;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.minilook.minilook.App;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.data.rx.RxBusEvent;
import com.minilook.minilook.data.type.DisplayType;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.login.LoginActivity;
import com.minilook.minilook.ui.product_detail.ProductDetailActivity;
import com.minilook.minilook.util.StringUtil;
import lombok.Setter;

public class ProductGridVH extends BaseViewHolder<ProductDataModel> {

    @BindView(R.id.img_product_thumb) ImageView thumbImageView;
    @BindView(R.id.img_curtain) View curtain;
    @BindView(R.id.txt_display_label) TextView displayLabelTextView;
    @BindView(R.id.img_scrap) ImageView scrapImageView;
    @BindView(R.id.txt_brand_name) TextView brandNameTextView;
    @BindView(R.id.txt_product_name) TextView productNameTextView;
    @BindView(R.id.txt_discount_percent) TextView discountPercentTextView;
    @BindView(R.id.txt_price) TextView priceTextView;

    @BindString(R.string.base_price_percent) String format_percent;

    @BindDrawable(R.drawable.ic_scrap_off) Drawable img_scrap_off;
    @BindDrawable(R.drawable.ic_scrap_on) Drawable img_scrap_on;

    @BindColor(R.color.color_FFEEEFF5) int color_FFEEEFF5;

    @Setter private boolean isShowScrap;
    @Setter private boolean isShowBrand;

    public ProductGridVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_product_type_grid, (ViewGroup) itemView, false));
    }

    @Override public void bind(ProductDataModel $data) {
        super.bind($data);

        Glide.with(context)
            .load(data.getImage_url())
            .placeholder(new ColorDrawable(color_FFEEEFF5))
            .into(thumbImageView);

        if (isShowScrap) {
            showScrap();
        } else {
            hideScrap();
        }

        if (isShowBrand) {
            showBrand();
            brandNameTextView.setText(data.getBrand_name());
        } else {
            hideBrand();
        }
        productNameTextView.setText(data.getProduct_name());

        if (data.getDisplay_code() == DisplayType.DISPLAY.getValue()) {
            hideCurtain();
            hideDisplayLabel();
        } else {
            showCurtain();
            showDisplayLabel();
            displayLabelTextView.setText(data.getDisplay_label());
        }

        if (data.isDiscount()) {
            discountPercentTextView.setText(String.format(format_percent, data.getDiscount_percent()));
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

    private void showScrap() {
        scrapImageView.setVisibility(View.VISIBLE);
    }

    private void hideScrap() {
        scrapImageView.setVisibility(View.GONE);
    }

    private void showBrand() {
        brandNameTextView.setVisibility(View.VISIBLE);
    }

    private void hideBrand() {
        brandNameTextView.setVisibility(View.GONE);
    }

    void onItemClick(View view) {
        ProductDetailActivity.start(context, data.getProduct_id());
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
