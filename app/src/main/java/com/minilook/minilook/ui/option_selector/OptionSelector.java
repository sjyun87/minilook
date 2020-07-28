package com.minilook.minilook.ui.option_selector;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindColor;
import butterknife.BindDimen;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.fondesa.recyclerviewdivider.DividerDecoration;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.product.ProductColorDataModel;
import com.minilook.minilook.data.model.product.ProductSizeDataModel;
import com.minilook.minilook.ui.option_selector.adpater.OptionSelectorColorAdapter;
import com.minilook.minilook.ui.option_selector.adpater.OptionSelectorSizeAdapter;
import com.minilook.minilook.util.DimenUtil;
import com.minilook.minilook.util.StringUtil;
import java.util.List;

public class OptionSelector extends FrameLayout {

    @BindView(R.id.curtain) View curtainView;
    @BindView(R.id.layout_option_buy_panel) ConstraintLayout buyPanel;
    @BindView(R.id.txt_selected_option) TextView selectedOptionTextView;
    @BindView(R.id.img_option_arrow) ImageView optionArrowImageView;
    @BindView(R.id.rcv_selected_product) RecyclerView selectedOptionRecyclerView;
    @BindView(R.id.txt_total_count) TextView totalCountTextView;
    @BindView(R.id.txt_total_price) TextView totalPriceTextView;

    @BindView(R.id.layout_option_select_panel) ConstraintLayout selectPanel;
    @BindView(R.id.txt_selected_color) TextView selectedColorTextView;
    @BindView(R.id.img_color_arrow) ImageView colorArrowImageView;
    @BindView(R.id.rcv_select_color) RecyclerView colorRecyclerView;
    @BindView(R.id.txt_selected_size) TextView selectedSizeTextView;
    @BindView(R.id.img_size_arrow) ImageView sizeArrowImageView;
    @BindView(R.id.rcv_select_size) RecyclerView sizeRecyclerView;

    @BindString(R.string.option_selector_total_count) String format_total_count;
    @BindString(R.string.option_selector_selected_color) String format_selected_color;

    @BindColor(R.color.color_FFA9A9A9) int color_FFA9A9A9;
    @BindColor(R.color.color_FF424242) int color_FF424242;

    @BindDimen(R.dimen.sp_7) int sp_7;

    private OptionSelectorColorAdapter colorAdapter;
    private OptionSelectorSizeAdapter sizeAdapter;

    private List<ProductColorDataModel> data;

    private boolean isColorSelectBoxOpened = false;
    private boolean isSizeSelectBoxOpened = false;

    public OptionSelector(@NonNull Context context) {
        this(context, null);
    }

    public OptionSelector(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OptionSelector(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public OptionSelector(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        initView();
        setupColorRecyclerView();
        setupSizeRecyclerView();
    }

    private void setupColorRecyclerView() {
        colorAdapter = new OptionSelectorColorAdapter();
        colorRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        colorRecyclerView.setAdapter(colorAdapter);
        DividerDecoration.builder(getContext())
            .size(DimenUtil.dpToPx(getContext(), 1))
            .color(color_FFA9A9A9)
            .showLastDivider()
            .build()
            .addTo(colorRecyclerView);
        colorAdapter.setOnColorSelectedListener(this::onColorSelected);
    }

    private void setupSizeRecyclerView() {
        sizeAdapter = new OptionSelectorSizeAdapter();
        sizeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        sizeRecyclerView.setAdapter(sizeAdapter);
        DividerDecoration.builder(getContext())
            .size(DimenUtil.dpToPx(getContext(), 1))
            .color(color_FFA9A9A9)
            .showLastDivider()
            .build()
            .addTo(sizeRecyclerView);
        sizeAdapter.setOnSizeSelectedListener(this::onSizeSelected);
    }

    private void initView() {
        ButterKnife.bind(this, inflate(getContext(), R.layout.layout_option_selector, this));
        setupTotalCount(0);
        setupTotalPrice(0);
    }

    public void setupData(List<ProductColorDataModel> model) {
        this.data = model;

        colorAdapter.set(model);
        colorAdapter.refresh();
        showColorSelectBox();
    }

    public void show() {
        showSelectPanel();
        showBuyPanel();
    }

    public void hide() {
        hideSelectPanel();
        hideBuyPanel();
    }

    private void setupTotalPrice(int price) {
        totalPriceTextView.setText(StringUtil.toDigit(price));
    }

    private void setupTotalCount(int count) {
        totalCountTextView.setText(String.format(format_total_count, count));
    }

    private void showCurtain() {
        curtainView.setVisibility(View.VISIBLE);
    }

    private void hideCurtain() {
        curtainView.setVisibility(View.GONE);
    }

    private void showBuyPanel() {
        YoYo.with(Techniques.SlideInUp)
            .duration(150)
            .onStart(animator -> {
                showCurtain();
                buyPanel.setVisibility(View.VISIBLE);
            })
            .playOn(buyPanel);
    }

    private void hideBuyPanel() {
        YoYo.with(Techniques.SlideOutDown)
            .duration(150)
            .onEnd(animator -> {
                buyPanel.setVisibility(View.GONE);
                hideCurtain();
            })
            .playOn(buyPanel);
    }

    private void showSelectPanel() {
        YoYo.with(Techniques.SlideInUp)
            .duration(150)
            .onStart(animator -> selectPanel.setVisibility(View.VISIBLE))
            .playOn(selectPanel);
    }

    private void hideSelectPanel() {
        YoYo.with(Techniques.SlideOutDown)
            .duration(150)
            .onEnd(animator -> {
                selectPanel.setVisibility(View.GONE);
            })
            .playOn(selectPanel);
    }

    private void handleColorSelectedBox() {
        if (isColorSelectBoxOpened) {
            showColorSelectBox();
        } else {
            hideColorSelectBox();
        }
    }

    private void showColorSelectBox() {
        isColorSelectBoxOpened = true;
        YoYo.with(Techniques.SlideInDown)
            .duration(150)
            .onStart(animator -> colorRecyclerView.setVisibility(VISIBLE))
            .playOn(colorRecyclerView);
        colorArrowImageView.animate().rotation(180).setDuration(150).start();
    }

    private void hideColorSelectBox() {
        isColorSelectBoxOpened = false;
        YoYo.with(Techniques.SlideOutUp)
            .duration(150)
            .onEnd(animator -> colorRecyclerView.setVisibility(GONE))
            .playOn(colorRecyclerView);
        colorArrowImageView.animate().rotation(0).setDuration(150).start();
    }

    private void onColorSelected(ProductColorDataModel data) {
        setupSelectedColor(data.getName());
        hideColorSelectBox();

        sizeAdapter.set(data.getSize());
        sizeAdapter.refresh();
    }

    private void handleSizeSelectedBox() {
        if (isSizeSelectBoxOpened) {
            showSizeSelectBox();
        } else {
            hideSizeSelectBox();
        }
    }

    private void showSizeSelectBox() {
        isSizeSelectBoxOpened = true;
        YoYo.with(Techniques.SlideInDown)
            .duration(150)
            .onStart(animator -> sizeRecyclerView.setVisibility(VISIBLE))
            .playOn(sizeRecyclerView);
        sizeArrowImageView.animate().rotation(180).setDuration(150).start();
    }

    private void hideSizeSelectBox() {
        isSizeSelectBoxOpened = false;
        YoYo.with(Techniques.SlideOutUp)
            .duration(150)
            .onEnd(animator -> sizeRecyclerView.setVisibility(GONE))
            .playOn(sizeRecyclerView);
        sizeArrowImageView.animate().rotation(0).setDuration(150).start();
    }

    private void setupSelectedColor(String color) {
        selectedColorTextView.setText(String.format(format_selected_color, color));
        selectedColorTextView.setTextColor(color_FF424242);
        selectedColorTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, sp_7);
    }

    private void onSizeSelected(ProductSizeDataModel data) {


    }

    @OnClick(R.id.curtain)
    void onCurtainClick() {
        hide();
    }

    // Buy Panel -------------------------------------------------------------------------------------------------------
    @OnClick(R.id.layout_select_box_panel)
    void onSelectBoxClick() {

    }

    @OnClick(R.id.txt_shopping_bag)
    void onShoppingBagClick() {

    }

    @OnClick(R.id.txt_buy_now)
    void onBuyNowClick() {

    }

    // Select Panel ----------------------------------------------------------------------------------------------------
    @OnClick(R.id.layout_color_box_panel)
    void onColorSelectBoxClick() {
        isColorSelectBoxOpened = !isColorSelectBoxOpened;
        handleColorSelectedBox();
    }

    @OnClick(R.id.layout_size_box_panel)
    void onSizeSelectBoxClick() {
        isSizeSelectBoxOpened = !isSizeSelectBoxOpened;
        handleSizeSelectedBox();
    }
}
