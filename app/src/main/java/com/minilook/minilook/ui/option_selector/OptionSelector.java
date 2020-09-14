package com.minilook.minilook.ui.option_selector;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
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
import com.minilook.minilook.data.model.shopping.ShoppingOptionDataModel;
import com.minilook.minilook.data.model.product.ProductOptionDataModel;
import com.minilook.minilook.data.model.product.ProductStockDataModel;
import com.minilook.minilook.ui.option_selector.adpater.OptionSelectorColorAdapter;
import com.minilook.minilook.ui.option_selector.adpater.OptionSelectorOptionAdapter;
import com.minilook.minilook.ui.option_selector.adpater.OptionSelectorSizeAdapter;
import com.minilook.minilook.ui.option_selector.viewholder.OptionSelectorOptionVH;
import com.minilook.minilook.util.DimenUtil;
import com.minilook.minilook.util.StringUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Setter;

public class OptionSelector extends FrameLayout implements OptionSelectorOptionVH.OnButtonClickListener {

    @BindView(R.id.curtain) View curtainView;
    @BindView(R.id.layout_option_buy_panel) ConstraintLayout buyPanel;
    @BindView(R.id.txt_selected_option) TextView selectedOptionTextView;
    @BindView(R.id.img_option_arrow) ImageView optionArrowImageView;
    @BindView(R.id.rcv_goods) RecyclerView goodsRecyclerView;
    @BindView(R.id.txt_total_count) TextView totalCountTextView;
    @BindView(R.id.txt_total_price) TextView totalPriceTextView;

    @BindView(R.id.layout_option_select_panel) ConstraintLayout selectPanel;
    @BindView(R.id.img_back) ImageView backImageView;
    @BindView(R.id.txt_selected_color) TextView selectedColorTextView;
    @BindView(R.id.img_color_arrow) ImageView colorArrowImageView;
    @BindView(R.id.rcv_select_color) RecyclerView colorRecyclerView;
    @BindView(R.id.txt_selected_size) TextView selectedSizeTextView;
    @BindView(R.id.img_size_arrow) ImageView sizeArrowImageView;
    @BindView(R.id.rcv_select_size) RecyclerView sizeRecyclerView;

    @BindString(R.string.option_selector_total_count) String format_total_count;
    @BindString(R.string.option_selector_selected_color) String format_selected_color;
    @BindString(R.string.option_selector_color_box_hint) String str_hint_color;
    @BindString(R.string.option_selector_available_quantity) String format_available_quantity;

    @BindColor(R.color.color_FFA9A9A9) int color_FFA9A9A9;
    @BindColor(R.color.color_FF424242) int color_FF424242;
    @BindColor(R.color.color_FF616161) int color_FF616161;

    @BindDimen(R.dimen.sp_6) int sp_6;
    @BindDimen(R.dimen.sp_7) int sp_7;

    @Setter OnButtonClickListener onButtonClickListener;

    private OptionSelectorColorAdapter colorAdapter;
    private OptionSelectorSizeAdapter sizeAdapter;
    private OptionSelectorOptionAdapter optionAdapter;

    private int product_price;
    private List<ProductOptionDataModel> options;

    private boolean isColorSelectBoxOpened = false;
    private boolean isSizeSelectBoxOpened = false;

    private ProductOptionDataModel selectedColorData;
    private ProductStockDataModel selectedSizeData;
    private Map<Integer, Integer> selectedData = new HashMap<>();

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
        setupGoodsRecyclerView();
    }

    private void initView() {
        ButterKnife.bind(this, inflate(getContext(), R.layout.layout_option_selector, this));
        setupTotalCount(0);
        setupTotalPrice(0);
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

    private void setupGoodsRecyclerView() {
        optionAdapter = new OptionSelectorOptionAdapter();
        optionAdapter.setOnButtonClickListener(this);
        goodsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        goodsRecyclerView.setAdapter(optionAdapter);
    }

    public void setupData(int price, List<ProductOptionDataModel> options) {
        this.product_price = price;
        this.options = options;

        colorAdapter.set(options);
        colorAdapter.refresh();
        showColorSelectBox();
    }

    private void resetData() {
        isColorSelectBoxOpened = false;
        isSizeSelectBoxOpened = false;
        selectedColorData = null;
        selectedSizeData = null;
        optionAdapter.clear();
        optionAdapter.refresh();
        selectedData.clear();
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
            .onEnd(animator -> selectPanel.setVisibility(View.GONE))
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

    private void onColorSelected(ProductOptionDataModel data) {
        this.selectedColorData = data;
        setupSelectedColor(data.getColor_name());
        hideColorSelectBox();

        sizeAdapter.set(data.getStocks());
        sizeAdapter.refresh();
        postDelayed(this::showSizeSelectBox, 150);
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

    private void resetSelectedColor() {
        selectedColorTextView.setText(str_hint_color);
        selectedColorTextView.setTextColor(color_FF616161);
        selectedColorTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, sp_6);
    }

    private void onSizeSelected(ProductStockDataModel data) {
        this.selectedSizeData = data;
        addSelectedData();

        hideSelectPanel();
        resetSelectedColor();
        hideSizeSelectBox();
        showColorSelectBox();
        sizeAdapter.clear();
        sizeAdapter.refresh();
    }

    private void addSelectedData() {
        int goods_id = selectedSizeData.getOption_id();
        if (!selectedData.containsKey(goods_id)) {
            ShoppingOptionDataModel model = new ShoppingOptionDataModel();
            model.setOption_id(goods_id);
            int price = product_price + selectedSizeData.getPrice_add();
            model.setPrice_sum(price);
            model.setColor_name(selectedColorData.getColor_name());
            model.setSize_name(selectedSizeData.getSize_name());
            int order_limit = selectedSizeData.getOrder_limit();
            int stock = selectedSizeData.getSize_stock();
            model.setOrder_available_quantity(Math.min(order_limit, stock));
            model.setQuantity(1);

            selectedData.put(goods_id, 1);
            optionAdapter.add(model);
            optionAdapter.refresh();
        } else {
            for (ShoppingOptionDataModel model : optionAdapter.get()) {
                if (model.getOption_id() == goods_id) {
                    if (model.getQuantity() >= model.getOrder_available_quantity()) {
                        Toast.makeText(getContext(),
                            String.format(format_available_quantity, model.getOrder_available_quantity()),
                            Toast.LENGTH_SHORT).show();
                    } else {
                        int currentCount = model.getQuantity();
                        model.setQuantity(currentCount + 1);
                        selectedData.put(goods_id, currentCount + 1);
                        optionAdapter.refresh();
                    }
                    break;
                }
            }
        }
        setupTotal();
    }

    private void setupTotal() {
        int totalPrice = 0;
        int totalCount = 0;
        for (ShoppingOptionDataModel model : optionAdapter.get()) {
            int count = model.getQuantity();
            totalCount += count;
            int price = model.getPrice_sum();
            totalPrice += (price * count);
        }
        setupTotalCount(totalCount);
        setupTotalPrice(totalPrice);
    }

    @OnClick(R.id.curtain)
    void onCurtainClick() {
        resetData();
        hide();
    }

    // Buy Panel -------------------------------------------------------------------------------------------------------
    @OnClick(R.id.layout_select_box_panel)
    void onSelectBoxClick() {
        showSelectPanel();
    }

    @OnClick(R.id.txt_shopping_bag)
    void onShoppingBagClick() {
        onButtonClickListener.onShoppingBagClick(optionAdapter.get());
        resetData();
        hide();
    }

    @OnClick(R.id.txt_buy_now)
    void onBuyNowClick() {
        onButtonClickListener.onBuyClick(optionAdapter.get());
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

    // Goods Adapter
    @Override public void onDeleteClick(ShoppingOptionDataModel data) {
        optionAdapter.remove(data);
        optionAdapter.refresh();
        selectedData.remove(data.getOption_id());
        setupTotal();
    }

    @Override public void onMinusClick() {
        optionAdapter.refresh();
        setupTotal();
    }

    @Override public void onPlusClick() {
        optionAdapter.refresh();
        setupTotal();
    }

    public interface OnButtonClickListener {
        void onShoppingBagClick(List<ShoppingOptionDataModel> optionData);

        void onBuyClick(List<ShoppingOptionDataModel> optionData);
    }
}
