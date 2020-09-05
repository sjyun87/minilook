package com.minilook.minilook.ui.market.viewholder.brand;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindColor;
import butterknife.BindDimen;
import butterknife.BindDrawable;
import butterknife.BindFont;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.fondesa.recyclerviewdivider.DividerDecoration;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.minilook.minilook.App;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.brand.BrandDataModel;
import com.minilook.minilook.data.model.common.StyleDataModel;
import com.minilook.minilook.data.model.market.MarketDataModel;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.data.rx.RxBusEvent;
import com.minilook.minilook.ui.base.BaseViewHolder;
import com.minilook.minilook.ui.brand.BrandActivity;
import com.minilook.minilook.ui.brand_detail.BrandDetailActivity;
import com.minilook.minilook.ui.login.LoginActivity;
import com.minilook.minilook.ui.market.viewholder.brand.adapter.MarketBrandMenuAdapter;
import com.minilook.minilook.ui.market.viewholder.brand.viewholder.MarketBrandMenuVH;
import com.minilook.minilook.util.DimenUtil;
import com.minilook.minilook.util.SpannableUtil;
import com.minilook.minilook.util.StringUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import jp.wasabeef.glide.transformations.CropCircleWithBorderTransformation;

public class MarketBrandVH extends BaseViewHolder<MarketDataModel> implements MarketBrandMenuVH.OnMenuClickListener {

    @BindView(R.id.txt_title) TextView titleTextView;
    @BindView(R.id.rcv_brand) RecyclerView menuRecyclerView;
    @BindView(R.id.img_thumb) ImageView thumbImageView;
    @BindView(R.id.img_logo) ImageView logoImageView;
    @BindView(R.id.img_scrap) ImageView scrapImageView;
    @BindView(R.id.txt_scrap) TextView scrapCountTextView;
    @BindView(R.id.txt_name) TextView nameTextView;
    @BindView(R.id.txt_tag) TextView tagTextView;
    @BindView(R.id.txt_desc) TextView descTextView;
    @BindViews({ R.id.img_style1, R.id.img_style2, R.id.img_style3 }) List<ImageView> styleImageViews;

    @BindString(R.string.base_tag) String format_tag;

    @BindColor(R.color.color_FFDBDBDB) int color_FFDBDBDB;

    @BindDimen(R.dimen.dp_6) int dp_6;

    @BindFont(R.font.nanum_square_eb) Typeface font_extrabold;

    @BindDrawable(R.drawable.placeholder_image) Drawable img_placeholder;
    @BindDrawable(R.drawable.placeholder_image_wide_21) Drawable img_placeholder_wide_21;
    @BindDrawable(R.drawable.placeholder_logo) Drawable img_placeholder_logo;
    @BindDrawable(R.drawable.ic_scrap_off) Drawable img_scrap_off;
    @BindDrawable(R.drawable.ic_scrap_on) Drawable img_scrap_on;

    private Gson gson = new Gson();

    private List<BrandDataModel> brandItems;
    private int selectedPosition = 0;

    private MarketBrandMenuAdapter menuAdapter;

    public MarketBrandVH(@NonNull View itemView) {
        super(LayoutInflater.from(itemView.getContext())
            .inflate(R.layout.item_market_brand, (ViewGroup) itemView, false));

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        menuRecyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        menuAdapter = new MarketBrandMenuAdapter();
        menuRecyclerView.setAdapter(menuAdapter);
        menuAdapter.setOnMenuClickListener(this);
        DividerDecoration.builder(context)
            .size(dp_6)
            .asSpace()
            .build()
            .addTo(menuRecyclerView);
        ViewCompat.setNestedScrollingEnabled(menuRecyclerView, false);
    }

    @Override public void bind(MarketDataModel $data) {
        super.bind($data);

        titleTextView.setText(getBoldText());

        brandItems = parseJsonToModel();

        menuAdapter.set(brandItems);
        menuAdapter.refresh();

        setupBrandData(brandItems.get(selectedPosition));
    }

    private SpannableString getBoldText() {
        SpannableString title = new SpannableString(data.getTitle());
        StringTokenizer tokenizer = new StringTokenizer(data.getBold_text(), ",");
        while (tokenizer.hasMoreTokens()) {
            SpannableUtil.fontSpan(title, tokenizer.nextToken(), font_extrabold);
        }
        return title;
    }

    private void setupBrandData(BrandDataModel model) {
        Glide.with(context)
            .load(model.getImage_url())
            .placeholder(img_placeholder_wide_21)
            .error(img_placeholder_wide_21)
            .transition(new DrawableTransitionOptions().crossFade())
            .into(thumbImageView);

        Glide.with(context)
            .load(model.getBrand_logo())
            .placeholder(img_placeholder_logo)
            .error(img_placeholder_logo)
            .apply(RequestOptions.bitmapTransform(
                new CropCircleWithBorderTransformation(DimenUtil.dpToPx(context, 1), color_FFDBDBDB)))
            .transition(new DrawableTransitionOptions().crossFade())
            .into(logoImageView);

        setupScrapImage(model.isScrap());
        scrapCountTextView.setText(StringUtil.toDigit(model.getScrap_cnt()));
        nameTextView.setText(model.getBrand_name());
        tagTextView.setText(getStyleTag(model.getStyles()));
        descTextView.setText(model.getBrand_desc());

        List<String> styleImages = model.getStyle_images();
        for (int i = 0; i < styleImageViews.size(); i++) {
            Glide.with(context)
                .load(styleImages.get(i))
                .placeholder(img_placeholder)
                .error(img_placeholder)
                .transition(new DrawableTransitionOptions().crossFade())
                .into(styleImageViews.get(i));
        }
    }

    private void setupScrapImage(boolean isScrap) {
        if (isScrap) {
            scrapImageView.setImageDrawable(img_scrap_on);
        } else {
            scrapImageView.setImageDrawable(img_scrap_off);
        }
    }

    private String getStyleTag(List<StyleDataModel> styles) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < styles.size(); i++) {
            if (i != 0) sb.append(" ");
            sb.append(String.format(format_tag, styles.get(i).getName()));
        }
        return sb.toString();
    }

    private List<BrandDataModel> parseJsonToModel() {
        List<BrandDataModel> items = gson.fromJson(data.getData(), new TypeToken<ArrayList<BrandDataModel>>() {
        }.getType());
        for (int i = 0; i < items.size(); i++) {
            items.get(i).setPosition(i);
            items.get(i).setSelect(i == selectedPosition);
        }
        return items;
    }

    @Override public void onMenuClick(int position) {
        brandItems.get(selectedPosition).setSelect(false);
        selectedPosition = position;
        brandItems.get(selectedPosition).setSelect(true);
        menuAdapter.refresh();

        setupBrandData(brandItems.get(selectedPosition));
    }

    @OnClick(R.id.layout_scrap_panel)
    void onScrapClick() {
        if (App.getInstance().isLogin()) {
            BrandDataModel model = brandItems.get(selectedPosition);
            model.setScrap(!model.isScrap());
            setupScrapImage(model.isScrap());
            RxBus.send(new RxBusEvent.RxBusEventBrandScrap(model.isScrap(), model));
        } else {
            LoginActivity.start(context);
        }
    }

    @OnClick(R.id.txt_more)
    void onMoreClick() {
        BrandActivity.start(context);
    }

    @OnClick(R.id.layout_brand_panel)
    void onBrandMoreClick() {
        int brand_id = brandItems.get(selectedPosition).getId();
        BrandDetailActivity.start(context, brand_id);
    }
}
