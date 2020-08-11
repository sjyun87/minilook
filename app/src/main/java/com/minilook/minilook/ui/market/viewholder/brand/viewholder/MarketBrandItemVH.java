package com.minilook.minilook.ui.market.viewholder.brand.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import butterknife.BindColor;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.brand.BrandDataModel;
import com.minilook.minilook.data.model.brand.BrandMenuDataModel;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.brand_detail.BrandDetailActivity;
import com.minilook.minilook.util.DimenUtil;
import java.util.List;
import jp.wasabeef.glide.transformations.CropCircleWithBorderTransformation;

public class MarketBrandItemVH extends BaseViewHolder<BrandMenuDataModel> {

    @BindView(R.id.img_thumb) ImageView thumbImageView;
    @BindView(R.id.img_logo) ImageView logoImageView;
    @BindView(R.id.txt_name) TextView nameTextView;
    @BindView(R.id.txt_tag) TextView tagTextView;
    @BindView(R.id.txt_desc) TextView descTextView;
    @BindViews({ R.id.img_style1, R.id.img_style2, R.id.img_style3 }) List<ImageView> styleImageViews;

    @BindColor(R.color.color_FFDBDBDB) int color_FFDBDBDB;

    public MarketBrandItemVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_market_brand_item, (ViewGroup) itemView, false));
    }

    @Override public void bind(BrandMenuDataModel $data) {
        super.bind($data);

        BrandDataModel model = data.getModel();

        Glide.with(context)
            .load(model.getUrl_thumb())
            .into(thumbImageView);

        Glide.with(context)
            .load(data.getModel().getUrl_logo())
            .apply(RequestOptions.bitmapTransform(
                new CropCircleWithBorderTransformation(DimenUtil.dpToPx(context, 1), color_FFDBDBDB)))
            .into(logoImageView);

        nameTextView.setText(model.getName());
        tagTextView.setText(model.getTag());
        descTextView.setText(model.getDesc());

        for (int i = 0; i < model.getImages().size(); i++) {
            Glide.with(context)
                .load(model.getImages().get(i))
                .into(styleImageViews.get(i));
        }
    }

    @OnClick(R.id.layout_scrap_panel)
    void onScrapClick() {

    }

    @OnClick(R.id.txt_go_detail)
    void onGoDetailClick() {
        BrandDetailActivity.start(context, data.getModel().getId());
    }
}
