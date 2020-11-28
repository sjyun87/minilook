package com.minilook.minilook.ui.scrapbook.view.brand.viewholder;

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
import butterknife.BindViews;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.minilook.minilook.App;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.brand.BrandDataModel;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.data.rx.RxBusEvent;
import com.minilook.minilook.ui.base._BaseViewHolder;
import com.minilook.minilook.ui.brand_detail.BrandDetailActivity;
import com.minilook.minilook.ui.login.LoginActivity;
import com.minilook.minilook.util.DimenUtil;
import java.util.List;
import jp.wasabeef.glide.transformations.CropCircleWithBorderTransformation;

public class ScrapbookBrandItemVH extends _BaseViewHolder<BrandDataModel> {

    @BindViews({ R.id.img_style1, R.id.img_style2, R.id.img_style3 }) List<ImageView> styleImageViews;
    @BindView(R.id.img_logo) ImageView logoImageView;
    @BindView(R.id.txt_name) TextView nameTextView;
    @BindView(R.id.txt_tag) TextView tagTextView;
    @BindView(R.id.img_scrap) ImageView scrapImageView;

    @BindColor(R.color.color_FFDBDBDB) int color_FFDBDBDB;

    @BindDrawable(R.drawable.ph_square) Drawable img_placeholder;
    @BindDrawable(R.drawable.ph_circle) Drawable img_placeholder_logo;

    public ScrapbookBrandItemVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_scrapbook_brand, (ViewGroup) itemView, false));
    }

    @Override public void bind(BrandDataModel $data) {
        super.bind($data);

        for (int i = 0; i < styleImageViews.size(); i++) {
            if (data.getStyleImages() != null && data.getStyleImages().size() > 0) {
                Glide.with(context)
                    .load(data.getStyleImages().get(i))
                    .placeholder(img_placeholder)
                    .error(img_placeholder)
                    .transition(new DrawableTransitionOptions().crossFade())
                    .into(styleImageViews.get(i));
            } else {
                Glide.with(context)
                    .load(img_placeholder)
                    .placeholder(img_placeholder)
                    .error(img_placeholder)
                    .transition(new DrawableTransitionOptions().crossFade())
                    .into(styleImageViews.get(i));
            }
        }

        Glide.with(context)
            .load(data.getBrandLogo())
            .placeholder(img_placeholder_logo)
            .error(img_placeholder_logo)
            .transition(new DrawableTransitionOptions().crossFade())
            .apply(RequestOptions.bitmapTransform(
                new CropCircleWithBorderTransformation(DimenUtil.dpToPx(context, 1), color_FFDBDBDB)))
            .into(logoImageView);

        nameTextView.setText(data.getBrandName());
        tagTextView.setText(data.getBrandTag());

        itemView.setOnClickListener(this::onItemClick);
    }

    void onItemClick(View view) {
        BrandDetailActivity.start(context, data.getBrandNo());
    }

    @OnClick(R.id.img_scrap)
    void onScrapClick() {
        if (App.getInstance().isLogin()) {
            data.setScrap(!data.isScrap());
            //RxBus.send(new RxBusEvent.RxBusEventBrandScrap(data.isScrap(), data));
        } else {
            LoginActivity.start(context);
        }
    }
}
