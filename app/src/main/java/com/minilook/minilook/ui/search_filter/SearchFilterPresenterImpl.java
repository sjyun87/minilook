package com.minilook.minilook.ui.search_filter;

import com.google.gson.Gson;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.common.CategoryDataModel;
import com.minilook.minilook.data.model.common.ColorDataModel;
import com.minilook.minilook.data.model.common.GenderDataModel;
import com.minilook.minilook.data.model.common.StyleDataModel;
import com.minilook.minilook.data.model.search.FilterDataModel;
import com.minilook.minilook.data.model.search.SearchOptionDataModel;
import com.minilook.minilook.data.network.search.SearchRequest;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.search_filter.di.SearchFilterArguments;
import java.util.ArrayList;
import java.util.List;
import timber.log.Timber;

public class SearchFilterPresenterImpl extends BasePresenterImpl implements SearchFilterPresenter {

    private static final int AGE_BABY_MONTH = 24;
    private static final int PRICE_STEP = 5000;
    private static final String SIZE_TYPE_BABY = "베이비";
    private static final String SIZE_TYPE_SHOES = "신발";
    private static final String SIZE_TYPE_ACCESSORIES = "악세사리";

    private final View view;
    private final BaseAdapterDataModel<GenderDataModel> genderAdapter;
    private final BaseAdapterDataModel<CategoryDataModel> categoryAdapter;
    private final BaseAdapterDataModel<ColorDataModel> colorAdapter;
    private final SearchRequest searchRequest;

    private Gson gson = new Gson();

    private int genderSelectedPosition = -1;
    private int categorySelectedPosition = -1;
    private int priceMaxStep;
    private int selectedMinStep;
    private int selectedMaxStep;

    private String genderCode = null;
    private int ageCode = 0;
    private boolean isShowDiscount = false;
    private boolean isShowStock = false;
    private String categoryCode = null;
    private int sizeType = -1;
    private int minPrice = -1;
    private int maxPrice = -1;
    private List<String> colorCodes = new ArrayList<>();
    private List<String> styleCodes = new ArrayList<>();

    public SearchFilterPresenterImpl(SearchFilterArguments args) {
        view = args.getView();
        genderAdapter = args.getGenderAdapter();
        categoryAdapter = args.getCategoryAdapter();
        colorAdapter = args.getColorAdapter();
        searchRequest = new SearchRequest();
    }

    @Override public void onCreate() {
        view.setupGenderRecyclerView();
        view.setupAgeSlider();
        view.setupCategoryRecyclerView();
        view.setupPriceSlider();
        view.setupColorRecyclerView();

        reqFilterOptions();
    }

    @Override public void onGenderSelected(GenderDataModel data) {
        if (data.isSelected()) {
            genderSelectedPosition = -1;
            genderCode = null;
            genderAdapter.get(data.getPosition()).setSelected(false);
        } else {
            if (genderSelectedPosition != -1 && genderSelectedPosition != data.getPosition()) {
                genderAdapter.get(genderSelectedPosition).setSelected(false);
            }
            genderSelectedPosition = data.getPosition();
            genderCode = data.getCode();
            genderAdapter.get(data.getPosition()).setSelected(true);
        }
        view.genderRefresh();
    }

    @Override public void onAgeChanged(float value) {
        int age = (int) value;
        int ageMonth;
        if (age <= AGE_BABY_MONTH) {    // 0 ~ 24개월
            view.setupAgeText(age, true);
            ageMonth = age;
        } else {    // 3 ~ 16세
            view.setupAgeText(age - 22, false);
            ageMonth = ((age - 22) * 12) - 6;
        }
        ageCode = ageMonth;
    }

    @Override public void onAttributeDiscountClick() {
        if (isShowDiscount) {
            view.setupUnselectedDiscount();
            isShowDiscount = false;
        } else {
            view.setupSelectedDiscount();
            isShowDiscount = true;
        }
    }

    @Override public void onAttributeStockClick() {
        if (isShowStock) {
            view.setupUnselectedStock();
            isShowStock = false;
        } else {
            view.setupSelectedStock();
            isShowStock = true;
        }
    }

    @Override public void onCategorySelected(CategoryDataModel data) {
        if (categorySelectedPosition != data.getPosition()) {
            if (categorySelectedPosition != -1) {
                categoryAdapter.get(categorySelectedPosition).setSelected(false);
            }
            categorySelectedPosition = data.getPosition();
            categoryAdapter.get(categorySelectedPosition).setSelected(true);
            view.categoryRefresh();
            categoryCode = data.getCode();
            if (data.getName().equals(SIZE_TYPE_BABY)) {
                sizeType = 2;
            } else if (data.getName().equals(SIZE_TYPE_SHOES)) {
                sizeType = 4;
            } else if (data.getName().equals(SIZE_TYPE_ACCESSORIES)) {
                sizeType = 5;
            } else {
                sizeType = 3;
            }
        } else {
            categoryAdapter.get(categorySelectedPosition).setSelected(false);
            categorySelectedPosition = -1;
            categoryCode = null;
            view.categoryRefresh();
        }
    }

    @Override public void onPriceChanged(List<Float> values) {
        int minStep = values.get(0).intValue();
        int maxStep = values.get(1).intValue();
        if (minStep == maxStep) {
            view.setupPriceValue(selectedMinStep, selectedMaxStep);
            return;
        }

        selectedMinStep = minStep;
        selectedMaxStep = maxStep;

        if (selectedMinStep == 0 && selectedMaxStep == priceMaxStep) {
            minPrice = -1;
            maxPrice = -1;
            view.setupPriceText(minPrice, maxPrice, 0);
        } else if (selectedMaxStep == priceMaxStep) {
            minPrice = selectedMinStep * PRICE_STEP;
            maxPrice = -1;
            view.setupPriceText(minPrice, maxPrice, 1);
        } else {
            minPrice = selectedMinStep * PRICE_STEP;
            maxPrice = selectedMaxStep * PRICE_STEP;
            view.setupPriceText(minPrice, maxPrice, 2);
        }
    }

    @Override public void onColorSelected(ColorDataModel data) {
        if (colorCodes.contains(data.getCode())) {
            colorCodes.remove(data.getCode());
            colorAdapter.get(data.getPosition()).setSelected(false);
        } else {
            colorCodes.add(data.getCode());
            colorAdapter.get(data.getPosition()).setSelected(true);
        }
        view.colorRefresh();
    }

    @Override public void onStyleSelected(StyleDataModel data) {
        if (styleCodes.contains(data.getCode())) {
            styleCodes.remove(data.getCode());
            view.unselectedStyleView(data.getPosition());
        } else {
            styleCodes.add(data.getCode());
            view.selectedStyleView(data.getPosition());
        }
    }

    @Override public void onResetClick() {
        resetGenderData();
        resetAgeData();
        resetShowDiscount();
        resetShowStock();
        resetCategoryData();
        resetPriceData();
        resetColorData();
        resetStyleData();
    }

    @Override public void onSearchClick() {
        SearchOptionDataModel model = new SearchOptionDataModel();
        model.setGender_code(genderCode);
        model.setAge(ageCode);
        model.setDiscount(isShowDiscount);
        model.setStock(isShowStock);
        model.setCategory_code(categoryCode);
        model.setPrice_min(minPrice);
        model.setPrice_max(maxPrice);
        model.setColor_codes(colorCodes);
        model.setStyle_codes(styleCodes);
        model.setType(sizeType);
        model.setColor_codes(colorCodes);



        model.setPage(1);
        model.setRow(30);
        model.setOrder("HGHST_PRC");

        addDisposable(searchRequest.getProducts(model)
            .compose(Transformer.applySchedulers())
            .subscribe(this::resProducts, Timber::e));
    }

    private void resProducts(BaseDataModel dataModel) {
        Timber.e(dataModel.getData().toString());
    }

    private void reqFilterOptions() {
        addDisposable(
            searchRequest.getFilterOptions("")
                .map(data -> gson.fromJson(data.getData(), FilterDataModel.class))
                .compose(Transformer.applySchedulers())
                .subscribe(this::resFilterOptions, Timber::e)
        );
    }

    private void resFilterOptions(FilterDataModel data) {
        genderAdapter.set(setupGenderDataInit(data.getGenders()));
        view.genderRefresh();

        categoryAdapter.set(setupCategoryDataInit(data.getCategories()));
        view.categoryRefresh();

        int minPriceLimit = data.getPrice_min();
        int maxPriceLimit = data.getPrice_max();
        priceMaxStep = (maxPriceLimit / PRICE_STEP) + 1;
        view.initPriceSlider(minPriceLimit, maxPriceLimit, priceMaxStep);

        colorAdapter.set(setupColorDataInit(data.getColors()));
        view.colorRefresh();

        setupStyleInit(data.getStyles());
    }

    private List<GenderDataModel> setupGenderDataInit(List<GenderDataModel> genders) {
        List<GenderDataModel> items = new ArrayList<>();
        for (int i = 0; i < genders.size(); i++) {
            GenderDataModel model = genders.get(i);
            model.setPosition(i);
            model.setSelected(false);
            items.add(model);
        }
        return items;
    }

    private void resetGenderData() {
        List<GenderDataModel> items = genderAdapter.get();
        for (int i = 0; i < items.size(); i++) {
            GenderDataModel model = items.get(i);
            model.setSelected(false);
        }
        view.genderRefresh();
        genderCode = null;
    }

    private void resetAgeData() {
        view.resetAgeSlider();
        ageCode = -1;
    }

    private void resetShowDiscount() {
        isShowDiscount = false;
        view.setupUnselectedDiscount();
    }

    private void resetShowStock() {
        isShowStock = false;
        view.setupUnselectedStock();
    }

    private List<CategoryDataModel> setupCategoryDataInit(List<CategoryDataModel> categories) {
        List<CategoryDataModel> items = new ArrayList<>();
        for (int i = 0; i < categories.size(); i++) {
            CategoryDataModel model = categories.get(i);
            model.setPosition(i);
            model.setSelected(false);
            items.add(model);
        }
        return items;
    }

    private void resetCategoryData() {
        for (CategoryDataModel model : categoryAdapter.get()) {
            model.setSelected(false);
        }
        view.categoryRefresh();
        categoryCode = null;
        sizeType = -1;
    }

    private void resetPriceData() {
        minPrice = -1;
        maxPrice = -1;
        view.resetPriceSlider();
    }

    private List<ColorDataModel> setupColorDataInit(List<ColorDataModel> colors) {
        List<ColorDataModel> items = new ArrayList<>();
        for (int i = 0; i < colors.size(); i++) {
            ColorDataModel model = colors.get(i);
            model.setPosition(i);
            model.setSelected(false);
            items.add(model);
        }
        return items;
    }

    private void resetColorData() {
        for (ColorDataModel model : colorAdapter.get()) {
            model.setSelected(false);
        }
        view.colorRefresh();
        colorCodes = new ArrayList<>();
    }

    private void setupStyleInit(List<StyleDataModel> styles) {
        for (int i = 0; i < styles.size(); i++) {
            StyleDataModel model = styles.get(i);
            model.setPosition(i);
            model.setSelected(false);
            view.addStyleItem(model);
        }
    }

    private void resetStyleData() {
        view.resetStyleView();
        styleCodes = new ArrayList<>();
    }
}