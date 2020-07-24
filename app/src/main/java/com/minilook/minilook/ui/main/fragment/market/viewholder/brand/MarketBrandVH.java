package com.minilook.minilook.ui.main.fragment.market.viewholder.brand;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import butterknife.BindColor;
import butterknife.BindView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.brand.BrandInfoDataModel;
import com.minilook.minilook.data.model.market.MarketDataModel;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.util.DimenUtil;
import com.minilook.minilook.util.StringUtil;
import io.reactivex.rxjava3.core.Observable;
import jp.wasabeef.glide.transformations.CropCircleWithBorderTransformation;

public class MarketBrandVH extends BaseViewHolder<MarketDataModel> {

    @BindView(R.id.txt_title) TextView titleTextView;
    @BindView(R.id.img_thumb) ImageView thumbImageView;
    @BindView(R.id.img_logo) ImageView logoImageView;
    @BindView(R.id.txt_scrap) TextView scrapTextView;
    @BindView(R.id.txt_name) TextView nameTextView;
    @BindView(R.id.txt_tag) TextView tagTextView;
    @BindView(R.id.txt_desc) TextView descTextView;
    @BindView(R.id.img_style1) ImageView style1ImageView;
    @BindView(R.id.img_style2) ImageView style2ImageView;
    @BindView(R.id.img_style3) ImageView style3ImageView;

    @BindColor(R.color.color_FFDBDBDB) int color_FFDBDBDB;

    private Gson gson = new Gson();

    public MarketBrandVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_market_brand, (ViewGroup) itemView, false));
    }

    @Override public void bind(MarketDataModel $data) {
        super.bind($data);

        titleTextView.setText(data.getTitle());

        BrandInfoDataModel brandModel = parseJsonToModel();

        Glide.with(context)
            .load(brandModel.getUrl_thumb())
            .into(thumbImageView);

        Glide.with(context)
            .load(brandModel.getUrl_logo())
            .apply(RequestOptions.bitmapTransform(
                new CropCircleWithBorderTransformation(DimenUtil.dpToPx(context, 1), color_FFDBDBDB)))
            .into(logoImageView);

        scrapTextView.setText(StringUtil.toDigit(brandModel.getScrap_cnt()));
        nameTextView.setText(brandModel.getName());
        tagTextView.setText(brandModel.getTag());
        descTextView.setText(brandModel.getDesc());

        Glide.with(context)
            .load("http://lookbook.minilook.co.kr/data/goods/M6QhdvJzsqBjF5475lAeUQaYgvJjGV.jpg")
            .into(style1ImageView);

        Glide.with(context)
            .load("http://lookbook.minilook.co.kr/data/goods/LjbuUaA6cFudJhbe8VZxHkvwvaAV7g.jpg")
            .into(style2ImageView);

        Glide.with(context)
            .load("http://lookbook.minilook.co.kr/data/goods/uaZCB3zCud59MEsQsxBBcunSpHEYqj.jpg")
            .into(style3ImageView);
    }

    private BrandInfoDataModel parseJsonToModel() {
        return Observable.fromIterable(data.getData())
            .map(json -> gson.fromJson(json, BrandInfoDataModel.class))
            .blockingFirst();
    }
}
