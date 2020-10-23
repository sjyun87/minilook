package com.minilook.minilook.ui.option_selector;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindColor;
import butterknife.BindDimen;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.fondesa.recyclerviewdivider.DividerDecoration;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.product.OptionColorDataModel;
import com.minilook.minilook.data.model.product.OptionDataModel;
import com.minilook.minilook.data.model.product.OptionProductDataModel;
import com.minilook.minilook.data.model.product.OptionSizeDataModel;
import com.minilook.minilook.data.model.shopping.ShoppingOptionDataModel;
import com.minilook.minilook.data.model.shopping.ShoppingProductDataModel;
import com.minilook.minilook.ui.option_selector.adapater.OptionSelectorColorAdapter;
import com.minilook.minilook.ui.option_selector.adapater.OptionSelectorProductAdapter;
import com.minilook.minilook.ui.option_selector.adapater.OptionSelectorShoppingAdapter;
import com.minilook.minilook.ui.option_selector.adapater.OptionSelectorSizeAdapter;
import com.minilook.minilook.ui.option_selector.viewholder.OptionSelectorShoppingVH;
import com.minilook.minilook.util.DimenUtil;
import com.minilook.minilook.util.StringUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import lombok.Setter;

public class OptionSelector extends FrameLayout implements OptionSelectorShoppingVH.OnButtonClickListener {

    @BindView(R.id.curtain) View curtainView;

    @BindView(R.id.layout_add_panel) ConstraintLayout addPanel;
    @BindView(R.id.img_back) ImageView backImageView;
    @BindView(R.id.txt_title) TextView titleTextView;
    @BindView(R.id.layout_selected_product_panel) LinearLayout selectedProductPanel;
    @BindView(R.id.txt_product_box_hint) TextView productBoxHintTextView;
    @BindView(R.id.img_product_thumb) ImageView productThumbImageView;
    @BindView(R.id.txt_product_index) TextView productIndexTextView;
    @BindView(R.id.txt_product_name) TextView productNameTextView;
    @BindView(R.id.txt_product_price) TextView productPriceTextView;
    @BindView(R.id.img_product_box_arrow) ImageView productBoxArrowImageView;
    @BindView(R.id.rcv_select_product) RecyclerView productRecyclerView;
    @BindView(R.id.layout_color_box_panel) LinearLayout colorBoxPanel;
    @BindView(R.id.txt_selected_color) TextView selectedColorTextView;
    @BindView(R.id.img_color_box_arrow) ImageView colorBoxArrowImageView;
    @BindView(R.id.rcv_select_color) RecyclerView colorRecyclerView;
    @BindView(R.id.layout_size_box_panel) LinearLayout sizeBoxPanel;
    @BindView(R.id.txt_selected_size) TextView selectedSizeTextView;
    @BindView(R.id.img_size_box_arrow) ImageView sizeBoxArrowImageView;
    @BindView(R.id.rcv_select_size) RecyclerView sizeRecyclerView;

    @BindView(R.id.layout_buy_panel) ConstraintLayout buyPanel;
    @BindView(R.id.layout_add_bonus_box_panel) LinearLayout bonusBoxPanel;
    @BindView(R.id.rcv_shopping) RecyclerView shoppingRecyclerView;
    @BindView(R.id.txt_total_count) TextView totalCountTextView;
    @BindView(R.id.txt_total_price) TextView totalPriceTextView;
    @BindView(R.id.txt_buy) TextView buyTextView;

    @BindString(R.string.option_selector_title) String str_add_panel_title;
    @BindString(R.string.option_selector_bonus_title) String str_add_panel_bonus_title;
    @BindString(R.string.option_selector_total_count) String format_total_count;
    @BindString(R.string.option_selector_selected_color) String format_selected_color;
    @BindString(R.string.option_selector_color_box_hint) String str_hint_color;
    @BindString(R.string.option_selector_available_quantity) String format_available_quantity;
    @BindString(R.string.option_selector_product_index) String format_product_index;
    @BindString(R.string.option_selector_bonus_product_index) String str_bonus_product;

    @BindColor(R.color.color_FFA9A9A9) int color_FFA9A9A9;
    @BindColor(R.color.color_FF8140E5) int color_FF8140E5;
    @BindColor(R.color.color_FF424242) int color_FF424242;
    @BindColor(R.color.color_FF616161) int color_FF616161;

    @BindDrawable(R.drawable.placeholder_image) Drawable placeholder_img;

    @BindDimen(R.dimen.sp_6) int sp_6;
    @BindDimen(R.dimen.sp_7) int sp_7;

    private OptionSelectorProductAdapter productAdapter;
    private OptionSelectorColorAdapter colorAdapter;
    private OptionSelectorSizeAdapter sizeAdapter;
    private OptionSelectorShoppingAdapter shoppingAdapter;

    private OptionProductDataModel selectedProductData;
    private OptionColorDataModel selectedColorData;
    private OptionSizeDataModel selectedSizeData;

    private boolean isProductBoxOpened = false;
    private boolean isColorBoxOpened = false;
    private boolean isSizeBoxOpened = false;

    private OptionDataModel options;
    private List<ShoppingOptionDataModel> selectedOptionData = new ArrayList<>();
    private Map<Integer, List<ShoppingOptionDataModel>> selectedData = new HashMap<>();

    @Setter OnButtonClickListener onButtonClickListener;

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
        setupAddPanel();
        setupBuyPanel();
    }

    private void initView() {
        ButterKnife.bind(this, inflate(getContext(), R.layout.layout_option_selector, this));
    }

    private void setupAddPanel() {
        setupProductRecyclerView();
        setupColorRecyclerView();
        setupSizeRecyclerView();
    }

    private void setupBuyPanel() {
        setupShoppingRecyclerView();
        setupTotalCount(0);
        setupTotalPrice(0);
    }

    private void setupShoppingRecyclerView() {
        shoppingAdapter = new OptionSelectorShoppingAdapter();
        shoppingAdapter.setOnButtonClickListener(this);
        shoppingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        shoppingRecyclerView.setAdapter(shoppingAdapter);
    }

    public void setupData(OptionDataModel options) {
        this.options = options;

        productAdapter.set(options.getProducts());
        productAdapter.refresh();
        showProductBox();

        if (options.getBonusProducts().size() > 0) {
            bonusBoxPanel.setVisibility(VISIBLE);
        }
    }

    private void setupProductRecyclerView() {
        productRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        productAdapter = new OptionSelectorProductAdapter();
        productAdapter.setOnProductSelectedListener(this::onProductSelected);
        productRecyclerView.setAdapter(productAdapter);
        DividerDecoration.builder(getContext())
            .size(DimenUtil.dpToPx(getContext(), 1))
            .color(color_FFA9A9A9)
            .showLastDivider()
            .build()
            .addTo(productRecyclerView);
    }

    private void setupColorRecyclerView() {
        colorRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        colorAdapter = new OptionSelectorColorAdapter();
        colorAdapter.setOnColorSelectedListener(this::onColorSelected);
        colorRecyclerView.setAdapter(colorAdapter);
        DividerDecoration.builder(getContext())
            .size(DimenUtil.dpToPx(getContext(), 1))
            .color(color_FFA9A9A9)
            .showLastDivider()
            .build()
            .addTo(colorRecyclerView);
    }

    private void setupSizeRecyclerView() {
        sizeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        sizeAdapter = new OptionSelectorSizeAdapter();
        sizeAdapter.setOnSizeSelectedListener(this::onSizeSelected);
        sizeRecyclerView.setAdapter(sizeAdapter);
        DividerDecoration.builder(getContext())
            .size(DimenUtil.dpToPx(getContext(), 1))
            .color(color_FFA9A9A9)
            .showLastDivider()
            .build()
            .addTo(sizeRecyclerView);
    }

    private void onProductSelected(OptionProductDataModel data) {
        this.selectedProductData = data;
        setupSelectedProduct(data);
        hideProductBox();

        colorAdapter.set(data.getColors());
        colorAdapter.refresh();
        resetSelectedColor();
        postDelayed(() -> {
            colorBoxPanel.setVisibility(VISIBLE);
            showColorBox();
        }, 150);
    }

    private void setupSelectedProduct(OptionProductDataModel data) {
        productBoxHintTextView.setVisibility(GONE);
        selectedProductPanel.setVisibility(VISIBLE);

        Glide.with(this)
            .load(data.getImage())
            .placeholder(placeholder_img)
            .error(placeholder_img)
            .transition(new DrawableTransitionOptions().crossFade())
            .into(productThumbImageView);

        if (data.isBonus()) {
            productIndexTextView.setText(str_bonus_product);
        } else {
            productIndexTextView.setText(
                String.format(format_product_index, String.format(Locale.KOREA, "%02d", data.getPosition() + 1)));
        }

        productNameTextView.setText(data.getProductName());
        productPriceTextView.setText(StringUtil.toDigit(data.getPrice()));
    }

    private void resetSelectedProduct() {
        productBoxHintTextView.setVisibility(VISIBLE);
        selectedProductPanel.setVisibility(GONE);
    }

    private void onColorSelected(OptionColorDataModel data) {
        this.selectedColorData = data;
        setupSelectedColor(data.getColorName());
        hideColorBox();

        sizeAdapter.set(data.getSizes());
        sizeAdapter.refresh();
        postDelayed(() -> {
            sizeBoxPanel.setVisibility(VISIBLE);
            showSizeBox();
        }, 150);
    }

    private void setupSelectedColor(String color) {
        selectedColorTextView.setText(String.format(format_selected_color, color));
        selectedColorTextView.setTextColor(color_FF424242);
        selectedColorTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, sp_7);
    }

    private void resetSelectedColor() {
        selectedColorTextView.setText(str_hint_color);
        selectedColorTextView.setTextColor(color_FFA9A9A9);
        selectedColorTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, sp_6);
    }

    private void onSizeSelected(OptionSizeDataModel data) {
        for (ShoppingOptionDataModel optionData : selectedOptionData) {
            if (optionData.getProductNo() == selectedProductData.getProductNo()
                && optionData.getOptionNo() == data.getOptionNo()) {
                Toast.makeText(getContext(), "이미 선택한 상품입니다.", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        this.selectedSizeData = data;
        addSelectedData();

        hideAddPanel();
        resetAddPanel();
    }

    private void addSelectedData() {
        int productNo = selectedProductData.getProductNo();
        int optionNo = selectedSizeData.getOptionNo();

        ShoppingOptionDataModel model = new ShoppingOptionDataModel();
        model.setOptionNo(optionNo);
        model.setProductNo(productNo);
        model.setBonus(selectedProductData.isBonus());
        model.setProductIndex(selectedProductData.getPosition() + 1);
        model.setProductName(selectedProductData.getProductName());
        model.setImage(selectedProductData.getImage());
        model.setDiscount(selectedProductData.isDiscount());
        model.setDiscountPercent(selectedProductData.getDiscountPercent());
        model.setPrice(selectedProductData.getPrice());
        model.setColorName(selectedColorData.getColorName());
        model.setSizeName(selectedSizeData.getSizeName());
        int order_limit = selectedSizeData.getOrderLimit();
        int stock = selectedSizeData.getSizeStock();
        model.setLimitQuantity(Math.min(order_limit, stock));
        model.setQuantity(1);
        model.setPriceAdd(selectedSizeData.getPriceAdd());
        model.setPriceSum(selectedProductData.getPrice() + selectedSizeData.getPriceAdd());
        selectedOptionData.add(model);

        shoppingAdapter.add(model);
        shoppingAdapter.refresh();

        if (!selectedData.containsKey(productNo)) {
            List<ShoppingOptionDataModel> items = new ArrayList<>();
            items.add(model);
            selectedData.put(productNo, items);
        } else {
            List<ShoppingOptionDataModel> items = selectedData.get(productNo);
            if (items != null) {
                items.add(model);
                selectedData.put(productNo, items);
            }
        }

        setupTotal();
        enableBuyButton();
    }

    private void setupTotal() {
        int totalPrice = 0;
        int totalCount = 0;
        for (ShoppingOptionDataModel model : selectedOptionData) {
            int count = model.getQuantity();
            totalCount += count;
            int price = model.getPriceSum();
            totalPrice += (price * count);
        }
        setupTotalCount(totalCount);
        setupTotalPrice(totalPrice);
    }

    private void setupTotalPrice(int price) {
        totalPriceTextView.setText(StringUtil.toDigit(price));
    }

    private void setupTotalCount(int count) {
        totalCountTextView.setText(String.format(format_total_count, count));
    }

    private void resetAddPanel() {
        hideProductBox();
        hideColorBox();
        hideSizeBox();
        selectedProductData = null;
        selectedColorData = null;
        selectedSizeData = null;

        colorBoxPanel.setVisibility(GONE);
        sizeBoxPanel.setVisibility(GONE);

        resetSelectedProduct();
        resetSelectedColor();
    }

    private void resetBuyPanel() {
        shoppingAdapter.clear();
        shoppingAdapter.refresh();
        selectedOptionData.clear();
        selectedData.clear();
    }

    private void resetData() {
        resetAddPanel();

        productAdapter.clear();
        productAdapter.refresh();
        colorAdapter.clear();
        colorAdapter.refresh();
        sizeAdapter.clear();
        sizeAdapter.refresh();

        resetBuyPanel();
    }

    public void show() {
        colorBoxPanel.setVisibility(GONE);
        sizeBoxPanel.setVisibility(GONE);

        showAddPanel();
        showBuyPanel();
    }

    public void hide() {
        hideAddPanel();
        hideBuyPanel();
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

    private void showAddPanel() {
        YoYo.with(Techniques.SlideInUp)
            .duration(150)
            .onStart(animator -> addPanel.setVisibility(View.VISIBLE))
            .playOn(addPanel);
    }

    private void hideAddPanel() {
        YoYo.with(Techniques.SlideOutDown)
            .duration(150)
            .onEnd(animator -> addPanel.setVisibility(View.GONE))
            .playOn(addPanel);
    }

    private void showCurtain() {
        curtainView.setVisibility(View.VISIBLE);
    }

    private void hideCurtain() {
        curtainView.setVisibility(View.GONE);
    }

    private void showProductBox() {
        isProductBoxOpened = true;
        YoYo.with(Techniques.SlideInDown)
            .duration(150)
            .onStart(animator -> productRecyclerView.setVisibility(VISIBLE))
            .playOn(productRecyclerView);
        productBoxArrowImageView.animate().rotation(180).setDuration(150).start();
    }

    private void hideProductBox() {
        isProductBoxOpened = false;
        YoYo.with(Techniques.SlideOutUp)
            .duration(150)
            .onEnd(animator -> productRecyclerView.setVisibility(GONE))
            .playOn(productRecyclerView);
        productBoxArrowImageView.animate().rotation(0).setDuration(150).start();
    }

    private void showColorBox() {
        isColorBoxOpened = true;
        YoYo.with(Techniques.SlideInDown)
            .duration(150)
            .onStart(animator -> colorRecyclerView.setVisibility(VISIBLE))
            .playOn(colorRecyclerView);
        colorBoxArrowImageView.animate().rotation(180).setDuration(150).start();
    }

    private void hideColorBox() {
        isColorBoxOpened = false;
        YoYo.with(Techniques.SlideOutUp)
            .duration(150)
            .onEnd(animator -> colorRecyclerView.setVisibility(GONE))
            .playOn(colorRecyclerView);
        colorBoxArrowImageView.animate().rotation(0).setDuration(150).start();
    }

    private void showSizeBox() {
        isSizeBoxOpened = true;
        YoYo.with(Techniques.SlideInDown)
            .duration(150)
            .onStart(animator -> sizeRecyclerView.setVisibility(VISIBLE))
            .playOn(sizeRecyclerView);
        sizeBoxArrowImageView.animate().rotation(180).setDuration(150).start();
    }

    private void hideSizeBox() {
        isSizeBoxOpened = false;
        YoYo.with(Techniques.SlideOutUp)
            .duration(150)
            .onEnd(animator -> sizeRecyclerView.setVisibility(GONE))
            .playOn(sizeRecyclerView);
        sizeBoxArrowImageView.animate().rotation(0).setDuration(150).start();
    }

    private void handleProductBox() {
        if (isProductBoxOpened) {
            showProductBox();
        } else {
            hideProductBox();
        }
    }

    private void handleColorBox() {
        if (isColorBoxOpened) {
            showColorBox();
        } else {
            hideColorBox();
        }
    }

    private void handleSizeBox() {
        if (isSizeBoxOpened) {
            showSizeBox();
        } else {
            hideSizeBox();
        }
    }

    private void enableBuyButton() {
        buyTextView.setEnabled(true);
        buyTextView.setBackgroundColor(color_FF8140E5);
    }

    private void disableBuyButton() {
        buyTextView.setEnabled(false);
        buyTextView.setBackgroundColor(color_FFA9A9A9);
    }

    @OnClick(R.id.curtain)
    void onCurtainClick() {
        resetData();
        hide();
    }

    // Add Panel -------------------------------------------------------------------------------------------------------
    @OnClick(R.id.layout_product_box_panel)
    void onProductBoxClick() {
        isProductBoxOpened = !isProductBoxOpened;
        handleProductBox();

        if (isProductBoxOpened) {
            hideColorBox();
            hideSizeBox();
        }
    }

    @OnClick(R.id.layout_color_box_panel)
    void onColorBoxClick() {
        isColorBoxOpened = !isColorBoxOpened;
        handleColorBox();

        if (isColorBoxOpened) {
            hideProductBox();
            hideSizeBox();
        }
    }

    @OnClick(R.id.layout_size_box_panel)
    void onSizeBoxClick() {
        isSizeBoxOpened = !isSizeBoxOpened;
        handleSizeBox();

        if (isSizeBoxOpened) {
            hideProductBox();
            hideColorBox();
        }
    }

    @OnClick(R.id.img_back)
    void onBackClick() {
        resetAddPanel();
        hideAddPanel();
    }

    // Buy Panel -------------------------------------------------------------------------------------------------------
    @OnClick(R.id.layout_add_box_panel)
    void onAddsBoxClick() {
        backImageView.setVisibility(VISIBLE);
        titleTextView.setText(str_add_panel_title);
        productAdapter.set(options.getProducts());
        productAdapter.refresh();
        showAddPanel();
        showProductBox();
    }

    @OnClick(R.id.layout_add_bonus_box_panel)
    void onAddBonusBoxClick() {
        if (selectedOptionData.size() == 0) {
            Toast.makeText(getContext(), "상품을 먼저 선택해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        for (ShoppingOptionDataModel optionData : selectedOptionData) {
            if (optionData.isBonus()) {
                Toast.makeText(getContext(), "이미 선택한 추가상품이 있습니다.", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        backImageView.setVisibility(VISIBLE);
        titleTextView.setText(str_add_panel_bonus_title);
        productAdapter.set(options.getBonusProducts());
        productAdapter.refresh();
        showAddPanel();
        showProductBox();
    }

    @OnClick(R.id.txt_buy)
    void onBuyClick() {
        List<ShoppingProductDataModel> shoppingProducts = new ArrayList<>();
        for (int key : selectedData.keySet()) {
            List<ShoppingOptionDataModel> shoppingOptions = selectedData.get(key);
            if (shoppingOptions != null && shoppingOptions.size() > 0) {
                ShoppingProductDataModel shoppingProductData = new ShoppingProductDataModel();
                shoppingProductData.setProductNo(shoppingOptions.get(0).getProductNo());
                shoppingProductData.setProductName(shoppingOptions.get(0).getProductName());
                shoppingProductData.setThumbUrl(shoppingOptions.get(0).getImage());
                shoppingProductData.setDiscount(shoppingOptions.get(0).isDiscount());
                shoppingProductData.setDiscountPercent(shoppingOptions.get(0).getDiscountPercent());
                shoppingProductData.setPrice(shoppingOptions.get(0).getPrice());
                shoppingProductData.setAddPointPercent(1);
                shoppingProductData.setOptions(shoppingOptions);
                shoppingProducts.add(shoppingProductData);
            }
        }
        onButtonClickListener.onBuyClick(shoppingProducts);
    }

    // Shopping Adapter
    @Override public void onDeleteClick(ShoppingOptionDataModel data) {
        shoppingAdapter.remove(data);
        shoppingAdapter.refresh();

        selectedOptionData.remove(data);
        List<ShoppingOptionDataModel> options = selectedData.get(data.getProductNo());
        if (options != null) options.remove(data);

        if (selectedOptionData.size() == 1 && selectedOptionData.get(0).isBonus()) {
            selectedOptionData.clear();
            selectedData.clear();
            shoppingAdapter.clear();
            shoppingAdapter.refresh();
        }
        setupTotal();

        if (selectedOptionData.size() == 0) disableBuyButton();
    }

    @Override public void onMinusClick() {
        shoppingAdapter.refresh();
        setupTotal();
    }

    @Override public void onPlusClick() {
        shoppingAdapter.refresh();
        setupTotal();
    }

    public interface OnButtonClickListener {
        void onBuyClick(List<ShoppingProductDataModel> shoppingProductData);
    }
}
