package com.minilook.minilook.ui.search_filter;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
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
import com.minilook.minilook.data.model.common.ColorDataModel;
import com.minilook.minilook.data.model.common.GenderDataModel;
import com.minilook.minilook.data.model.common.StyleDataModel;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.base.widget.StyleView;
import com.minilook.minilook.ui.search_filter.adapter.FilterCategoryAdapter;
import com.minilook.minilook.ui.search_filter.adapter.FilterColorAdapter;
import com.minilook.minilook.ui.search_filter.adapter.FilterGenderAdapter;
import com.minilook.minilook.ui.search_filter.di.SearchFilterArguments;
import com.minilook.minilook.util.StringUtil;
import com.nex3z.flowlayout.FlowLayout;

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
    @BindView(R.id.img_age_reset) ImageView ageResetImageView;
    @BindView(R.id.txt_discount_caption) TextView discountCaptionTextView;
    @BindView(R.id.img_discount_check) ImageView discountCheckImageView;
    @BindView(R.id.txt_stock_caption) TextView stockCaptionTextView;
    @BindView(R.id.img_stock_check) ImageView stockCheckImageView;
    @BindView(R.id.rcv_category) RecyclerView categoryRecyclerView;
    @BindView(R.id.slider_price) RangeSlider priceSlider;
    @BindView(R.id.txt_price_min) TextView minPriceTextView;
    @BindView(R.id.txt_price_max) TextView maxPriceTextView;
    @BindView(R.id.txt_price) TextView priceTextView;
    @BindView(R.id.rcv_color) RecyclerView colorRecyclerView;
    @BindView(R.id.layout_style_item_panel) FlowLayout styleItemPanel;

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
    @BindColor(R.color.color_FFEEEFF5) int color_FFEEEFF5;
    @BindDrawable(R.drawable.ic_checkbox1_off) Drawable img_check_off;
    @BindDrawable(R.drawable.ic_checkbox1_on) Drawable img_check_on;
    @BindFont(R.font.nanum_square_r) Typeface font_regular;
    @BindFont(R.font.nanum_square_b) Typeface font_bold;

    private SearchFilterPresenter presenter;
    private FilterGenderAdapter genderAdapter = new FilterGenderAdapter();
    private BaseAdapterDataView<GenderDataModel> genderAdapterView = genderAdapter;
    private FilterCategoryAdapter categoryAdapter = new FilterCategoryAdapter();
    private BaseAdapterDataView<CategoryDataModel> categoryAdapterView = categoryAdapter;
    private FilterColorAdapter colorAdapter = new FilterColorAdapter();
    private BaseAdapterDataView<ColorDataModel> colorAdapterView = colorAdapter;

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
            .colorAdapter(colorAdapter)
            .build();
    }

    @Override public void setupGenderRecyclerView() {
        genderRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        genderAdapter.setListener(presenter::onGenderSelected);
        genderRecyclerView.setAdapter(genderAdapter);
        DividerDecoration.builder(this)
            .size(dp_5)
            .asSpace()
            .build()
            .addTo(genderRecyclerView);
        ViewCompat.setNestedScrollingEnabled(genderRecyclerView, false);
    }

    @Override public void genderRefresh() {
        genderAdapterView.refresh();
    }

    @Override public void setupAgeSlider() {
        ageSlider.addOnChangeListener((slider, value, fromUser) -> {
            presenter.onAgeChanged(value);
        });
    }

    @Override public void setupAgeText(int age, boolean isBaby) {
        String text;
        if (isBaby) {
            text = String.format(format_month, age);
        } else {
            text = String.format(format_year, age);
        }
        ageTextView.setText(text);
    }

    @Override public void resetAgeText() {
        ageTextView.setText(format_all);
    }

    @Override public void enableAgeSlider() {
        ColorStateList color = ColorStateList.valueOf(color_FF8140E5);
        ageSlider.setThumbTintList(color);
    }

    @Override public void disableAgeSlider() {
        ColorStateList color = ColorStateList.valueOf(color_FFEEEFF5);
        ageSlider.setThumbTintList(color);
    }

    @Override public void showAgeResetButton() {
        ageResetImageView.setVisibility(View.VISIBLE);
    }

    @Override public void hideAgeResetButton() {
        ageResetImageView.setVisibility(View.GONE);
    }

    @Override public void resetAgeSlider() {
        ageSlider.setValue(0f);
    }

    @Override public void setupCategoryRecyclerView() {
        categoryRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        categoryAdapter.setListener(presenter::onCategorySelected);
        categoryRecyclerView.setAdapter(categoryAdapter);
        ViewCompat.setNestedScrollingEnabled(categoryRecyclerView, false);
    }

    @Override public void categoryRefresh() {
        categoryAdapterView.refresh();
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

    @Override public void setupPriceValue(int currentMinStep, int currentMaxStep) {
        priceSlider.setValues((float) currentMinStep, (float) currentMaxStep);
    }

    @Override public void resetPriceSlider() {
        priceSlider.setValues(0f, priceSlider.getValueTo());
    }

    @Override public void setupPriceText(int minPrice, int maxPrice, int type) {
        String text = null;
        switch (type) {
            case 0:     // All
                text = format_price_all;
                break;
            case 1:     // More than
                text = String.format(format_price_range, StringUtil.toDigit(minPrice), "").trim();
                break;
            case 2:     // Range
                text = String.format(format_price_range, StringUtil.toDigit(minPrice), StringUtil.toDigit(maxPrice));
                break;
        }
        priceTextView.setText(text);
    }

    @Override public void setupColorRecyclerView() {
        colorRecyclerView.setLayoutManager(new GridLayoutManager(this, 5));
        colorAdapter.setListener(presenter::onColorSelected);
        colorRecyclerView.setAdapter(colorAdapter);
        ViewCompat.setNestedScrollingEnabled(colorRecyclerView, false);
    }

    @Override public void colorRefresh() {
        colorAdapterView.refresh();
    }

    @Override public void addStyleItem(StyleDataModel model) {
        StyleView styleView = StyleView.builder()
            .context(this)
            .model(model)
            .listener(presenter::onStyleSelected)
            .build();
        styleItemPanel.addView(styleView);
    }

    @Override public void selectedStyleView(int position) {
        ((StyleView) styleItemPanel.getChildAt(position)).selected();
    }

    @Override public void unselectedStyleView(int position) {
        ((StyleView) styleItemPanel.getChildAt(position)).unselected();
    }

    @Override public void resetStyleView() {
        for (int i = 0; i < styleItemPanel.getChildCount(); i++) {
            ((StyleView) styleItemPanel.getChildAt(i)).unselected();
        }
    }

    @OnClick(R.id.img_age_reset)
    void onAgeResetClick() {
        presenter.onAgeResetClick();
    }

    @OnClick(R.id.layout_attr_discount_panel)
    void onAttributeDiscountClick() {
        presenter.onAttributeDiscountClick();
    }

    @OnClick(R.id.layout_attr_stock_panel)
    void onAttributeStockClick() {
        presenter.onAttributeStockClick();
    }

    @OnClick(R.id.txt_reset)
    void onResetClick() {
        presenter.onResetClick();
    }

    @OnClick(R.id.txt_search)
    void onSearchClick() {
        presenter.onSearchClick();
    }
}
