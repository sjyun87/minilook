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
import com.minilook.minilook.data.code.DisplayCode;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.ui.base._BaseViewHolder;
import com.minilook.minilook.ui.login.LoginActivity;
import com.minilook.minilook.ui.main.MainPresenterImpl;
import com.minilook.minilook.ui.product_detail.ProductDetailActivity;
import com.minilook.minilook.util.StringUtil;
import lombok.Setter;

public class ProductGridVH extends _BaseViewHolder<ProductDataModel> {

    @BindView(R.id.img_product_thumb) ImageView thumbImageView;
    @BindView(R.id.img_curtain) View curtain;
    @BindView(R.id.txt_display_label) TextView displayLabelTextView;
    @BindView(R.id.img_scrap) ImageView scrapImageView;
    @BindView(R.id.txt_brand_name) TextView brandNameTextView;
    @BindView(R.id.txt_product_name) TextView productNameTextView;
    @BindView(R.id.txt_discount_percent) TextView discountPercentTextView;
    @BindView(R.id.txt_price) TextView priceTextView;

    @BindString(R.string.base_percent) String format_percent;

    @BindDrawable(R.drawable.ph_square) Drawable img_placeholder;
    @BindDrawable(R.drawable.ic_scrap_off) Drawable img_scrap_off;
    @BindDrawable(R.drawable.ic_scrap_on) Drawable img_scrap_on;

    @Setter private boolean isShowBrand;

    public ProductGridVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_product_type_grid, (ViewGroup) itemView, false));
    }

    @Override public void bind(ProductDataModel $data) {
        super.bind($data);

        Glide.with(context)
            .load(data.getImageUrl())
            .placeholder(img_placeholder)
            .error(img_placeholder)
            .transition(new DrawableTransitionOptions().crossFade())
            .into(thumbImageView);

        if (isShowBrand) {
            showBrand();
            brandNameTextView.setText(data.getBrandName());
        } else {
            hideBrand();
        }
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
        ProductDetailActivity.start(context, data.getProductNo());
    }

    @OnClick(R.id.img_scrap)
    void onScrapClick() {
        if (App.getInstance().isLogin()) {
            data.setScrap(!data.isScrap());
            if (data.isScrap()) {
                data.setScrapCount(data.getScrapCount() + 1);
            } else {
                data.setScrapCount(data.getScrapCount() - 1);
            }
            setupScrapImage();
            RxBus.send(new MainPresenterImpl.RxBusEventUpdateProductScrap(data));
        } else {
            LoginActivity.start(context);
        }
    }
}
