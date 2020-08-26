package com.minilook.minilook.ui.search_filter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindColor;
import butterknife.BindDimen;
import butterknife.BindDrawable;
import butterknife.BindFont;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import com.fondesa.recyclerviewdivider.DividerDecoration;
import com.google.android.material.slider.RangeSlider;
import com.google.android.material.slider.Slider;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.common.CategoryDataModel;
import com.minilook.minilook.data.model.common.GenderDataModel;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.search_filter.adapter.FilterCategoryAdapter;
import com.minilook.minilook.ui.search_filter.adapter.FilterGenderAdapter;
import com.minilook.minilook.ui.search_filter.di.SearchFilterArguments;
import com.minilook.minilook.util.StringUtil;

public class SearchFilterActivity extends BaseActivity implements SearchFilterPresenter.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, SearchFilterActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    @BindView(R.id.rcv_gender) RecyclerView genderRecyclerView;
    @BindView(R.id.slider_gender) Slider ageSlider;
    @BindView(R.id.txt_age) TextView ageTextView;
    @BindView(R.id.txt_discount_caption) TextView discountCaptionTextView;
    @BindView(R.id.img_discount_check) ImageView discountCheckImageView;
    @BindView(R.id.txt_stock_caption) TextView stockCaptionTextView;
    @BindView(R.id.img_stock_check) ImageView stockCheckImageView;
    @BindView(R.id.rcv_category) RecyclerView categoryRecyclerView;
    @BindView(R.id.slider_price) RangeSlider priceSlider;
    @BindView(R.id.txt_price_min) TextView minPriceTextView;
    @BindView(R.id.txt_price_max) TextView maxPriceTextView;
    @BindView(R.id.txt_price) TextView priceTextView;

    @BindDimen(R.dimen.dp_5) int dp_5;
    @BindString(R.string.search_filter_age_all) String format_all;
    @BindString(R.string.search_filter_age_month) String format_month;
    @BindString(R.string.search_filter_age_year) String format_year;
    @BindString(R.string.search_filter_price_max) String format_price_max;
    @BindString(R.string.search_filter_price_all) String format_price_all;
    @BindString(R.string.search_filter_price_limit) String format_price_limit;
    @BindString(R.string.search_filter_price_range) String format_price_range;
    @BindColor(R.color.color_FF232323) int color_FF232323;
    @BindColor(R.color.color_FF8140E5) int color_FF8140E5;
    @BindDrawable(R.drawable.ic_checkbox1_off) Drawable img_check_off;
    @BindDrawable(R.drawable.ic_checkbox1_on) Drawable img_check_on;
    @BindFont(R.font.nanum_square_r) Typeface font_regular;
    @BindFont(R.font.nanum_square_b) Typeface font_bold;

    private SearchFilterPresenter presenter;
    private FilterGenderAdapter genderAdapter = new FilterGenderAdapter();
    private BaseAdapterDataView<GenderDataModel> genderAdapterView = genderAdapter;
    private FilterCategoryAdapter categoryAdapter = new FilterCategoryAdapter();
    private BaseAdapterDataView<CategoryDataModel> categoryAdapterView = categoryAdapter;

    @Override protected int getLayoutID() {
        return R.layout.activity_search_filter;
    }

    @Override protected void createPresenter() {
        presenter = new SearchFilterPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private SearchFilterArguments provideArguments() {
        return SearchFilterArguments.builder()
            .view(this)
            .genderAdapter(genderAdapter)
            .categoryAdapter(categoryAdapter)
            .build();
    }

    @Override public void setupGenderRecyclerView() {
        genderRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        genderAdapter.setListener(presenter::onGenderSelected);
        genderRecyclerView.setAdapter(genderAdapter);
        DividerDecoration.builder(this)
            .size(dp_5)
            .asSpace()
            .build()
            .addTo(genderRecyclerView);
    }

    @Override public void genderRefresh() {
        genderAdapterView.refresh();
    }

    @Override public void setupAgeSlider() {
        ageSlider.addOnChangeListener((slider, value, fromUser) -> presenter.onAgeChanged(value));
    }

    @Override public void setupCategoryRecyclerView() {
        categoryRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        categoryAdapter.setListener(presenter::onCategorySelected);
        categoryRecyclerView.setAdapter(categoryAdapter);
    }

    @Override public void categoryRefresh() {
        categoryAdapterView.refresh();
    }

    @Override public void setupAgeText(int age, boolean isBaby) {
        String text;
        if (isBaby) {
            text = age == 0 ? format_all : String.format(format_month, age);
        } else {
            text = String.format(format_year, age);
        }
        ageTextView.setText(text);
    }

    @Override public void setupSelectedDiscount() {
        discountCaptionTextView.setTextColor(color_FF8140E5);
        discountCaptionTextView.setTypeface(font_bold);
        discountCheckImageView.setImageDrawable(img_check_on);
    }

    @Override public void setupUnselectedDiscount() {
        discountCaptionTextView.setTextColor(color_FF232323);
        discountCaptionTextView.setTypeface(font_regular);
        discountCheckImageView.setImageDrawable(img_check_off);
    }

    @Override public void setupSelectedStock() {
        stockCaptionTextView.setTextColor(color_FF8140E5);
        stockCaptionTextView.setTypeface(font_bold);
        stockCheckImageView.setImageDrawable(img_check_on);
    }

    @Override public void setupUnselectedStock() {
        stockCaptionTextView.setTextColor(color_FF232323);
        stockCaptionTextView.setTypeface(font_regular);
        stockCheckImageView.setImageDrawable(img_check_off);
    }

    @Override public void setupPriceSlider() {
        priceSlider.addOnChangeListener((slider, value, fromUser) -> presenter.onPriceChanged(slider.getValues()));
    }

    @Override public void initPriceSlider(int min, int max, int step) {
        priceSlider.setValueTo((float) step);
        priceSlider.setValues((float) min, (float) step);

        minPriceTextView.setText(String.valueOf(min));
        maxPriceTextView.setText(String.format(format_price_max, StringUtil.toDigit(max)));
    }

    @Override public void setupPriceText(int minPrice, int maxPrice, boolean isMinPriceLimit, boolean isMaxPriceLimit) {
        String text;
        if (isMinPriceLimit) {
            text = String.format(format_price_range,
                String.format(format_price_limit, StringUtil.toDigit(minPrice)), "").trim();
        } else if (isMaxPriceLimit) {
            if (minPrice == 0) {
                text = format_price_all;
            } else {
                text = String.format(format_price_range,
                    StringUtil.toDigit(minPrice), String.format(format_price_limit, StringUtil.toDigit(maxPrice)));
            }
        } else {
            text = String.format(format_price_range,
                StringUtil.toDigit(minPrice), StringUtil.toDigit(maxPrice));
        }
        priceTextView.setText(text);
    }

    @OnClick(R.id.layout_attr_discount_panel)
    void onAttributeDiscountClick() {
        presenter.onAttributeDiscountClick();
    }

    @OnClick(R.id.layout_attr_stock_panel)
    void onAttributeStockClick() {
        presenter.onAttributeStockClick();
    }
}
