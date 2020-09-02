package com.minilook.minilook.ui.scrapbook.view.brand.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import butterknife.BindColor;
import butterknife.BindView;
import butterknife.BindViews;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.brand.BrandDataModel;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.brand_detail.BrandDetailActivity;
import com.minilook.minilook.util.DimenUtil;
import com.minilook.minilook.util.StringUtil;
import java.util.List;
import jp.wasabeef.glide.transformations.CropCircleWithBorderTransformation;

public class ScrapbookBrandItemVH extends BaseViewHolder<BrandDataModel> {

    @BindViews({ R.id.img_style1, R.id.img_style2, R.id.img_style3 }) List<ImageView> styleImageViews;
    @BindView(R.id.img_logo) ImageView logoImageView;
    @BindView(R.id.txt_name) TextView nameTextView;
    @BindView(R.id.txt_tag) TextView tagTextView;
    @BindView(R.id.img_scrap) ImageView scrapImageView;
    @BindView(R.id.txt_scrap_count) TextView scrapCountTextView;

    @BindColor(R.color.color_FFDBDBDB) int color_FFDBDBDB;

    public ScrapbookBrandItemVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_scrapbook_brand, (ViewGroup) itemView, false));
    }

    @Override public void bind(BrandDataModel $data) {
        super.bind($data);

        Glide.with(context)
            .load(data.getBrand_logo())
            .apply(RequestOptions.bitmapTransform(
                new CropCircleWithBorderTransformation(DimenUtil.dpToPx(context, 1), color_FFDBDBDB)))
            .into(logoImageView);

        nameTextView.setText(data.getBrand_name());
        tagTextView.setText(data.getBrand_tag());
        scrapCountTextView.setText(StringUtil.toDigit(data.getScrap_cnt()));

        itemView.setOnClickListener(this::onItemClick);
    }

    void onItemClick(View view) {
        BrandDetailActivity.start(context, data.getId());
    }
}
