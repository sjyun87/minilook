package com.minilook.minilook.ui.brand.viewholder;

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
import butterknife.BindView;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.brand.BrandDataModel;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.data.rx.RxBusEvent;
import com.minilook.minilook.ui.base._BaseViewHolder;
import com.minilook.minilook.ui.brand_detail.BrandDetailActivity;
import com.minilook.minilook.util.DimenUtil;
import jp.wasabeef.glide.transformations.CropCircleWithBorderTransformation;

public class BrandVH extends _BaseViewHolder<BrandDataModel> {

    @BindView(R.id.img_logo) ImageView logoImageView;
    @BindView(R.id.txt_name) TextView nameTextView;
    @BindView(R.id.txt_tag) TextView tagTextView;
    @BindView(R.id.img_scrap) ImageView scrapImageView;

    @BindColor(R.color.color_FFEEEFF5) int color_FFEEEFF5;
    @BindColor(R.color.color_FFDBDBDB) int color_FFDBDBDB;

    @BindDrawable(R.drawable.ic_scrap_off) Drawable img_scrap_off;
    @BindDrawable(R.drawable.ic_scrap_on) Drawable img_scrap_on;

    public BrandVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_brand, (ViewGroup) itemView, false));
    }

    @Override public void bind(BrandDataModel $data) {
        super.bind($data);
        Glide.with(context)
            .load(data.getBrandLogo())
            .placeholder(new ColorDrawable(color_FFEEEFF5))
            .apply(RequestOptions.bitmapTransform(
                new CropCircleWithBorderTransformation(DimenUtil.dpToPx(context, 1), color_FFDBDBDB)))
            .into(logoImageView);

        nameTextView.setText(data.getBrandName());
        tagTextView.setText(data.getBrandTag().replace(",", " "));

        setupScrapImage(data.isScrap());

        itemView.setOnClickListener(this::onItemClick);
    }

    private void setupScrapImage(boolean isScrap) {
        if (isScrap) {
            scrapImageView.setImageDrawable(img_scrap_on);
        } else {
            scrapImageView.setImageDrawable(img_scrap_off);
        }
    }

    void onItemClick(View view) {
        BrandDetailActivity.start(context, data.getBrandNo());
    }

    @OnClick(R.id.img_scrap)
    void onScrapClick() {
        data.setScrap(!data.isScrap());
        setupScrapImage(data.isScrap());
        RxBus.send(new RxBusEvent.RxBusEventBrandScrap(data.isScrap(), data));
    }
}
