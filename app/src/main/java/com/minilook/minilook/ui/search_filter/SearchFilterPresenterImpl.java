package com.minilook.minilook.ui.search_filter;

import com.google.gson.Gson;
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
    private static final String SIZE_TYPE_ACCESSORIES = "악세사";

    private final View view;
    private final BaseAdapterDataModel<GenderDataModel> genderAdapter;
    private final BaseAdapterDataModel<CategoryDataModel> categoryAdapter;
    private final BaseAdapterDataModel<ColorDataModel> colorAdapter;
    private final SearchRequest searchRequest;

    private Gson gson = new Gson();

    private int genderSelectedPosition = 0;
    private int categorySelectedPosition = -1;
    private int limitMinPrice;
    private int limitMaxPrice;

    private String genderCode;
    private int ageCode;
    private boolean isShowDiscount = false;
    private boolean isShowStock = false;
    private String categoryCode;
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
        if (genderSelectedPosition != data.getPosition()) {
            genderCode = data.getCode();
            genderAdapter.get(genderSelectedPosition).setSelected(false);
            genderSelectedPosition = data.getPosition();
            genderAdapter.get(genderSelectedPosition).setSelected(true);
            view.genderRefresh();
        }
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
        int selectedMinStep = values.get(0).intValue();
        int selectedMaxStep = values.get(1).intValue();

        int selectedMinPrice = selectedMinStep * PRICE_STEP;
        int selectedMaxPrice = selectedMaxStep * PRICE_STEP;

        if (selectedMinPrice > limitMaxPrice) {
            view.setupPriceText(limitMaxPrice, limitMaxPrice, true, true);
            minPrice = -1;
            maxPrice = -1;
        } else if (selectedMaxPrice > limitMaxPrice) {
            view.setupPriceText(selectedMinPrice, limitMaxPrice, false, true);
            minPrice = selectedMinPrice;
            maxPrice = -1;
        } else {
            view.setupPriceText(selectedMinPrice, selectedMaxPrice, false, false);
            minPrice = selectedMinPrice;
            maxPrice = selectedMaxPrice;
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
        model.setBrand_id(-1);
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

        Timber.e(model.toString());
    }

    private void reqFilterOptions() {
        addDisposable(
            searchRequest.getFilterOptions()
                .map(data -> gson.fromJson(data.getData(), FilterDataModel.class))
                .compose(Transformer.applySchedulers())
                .subscribe(this::resFilterOptions, Timber::e)
        );
    }

    private void resFilterOptions(FilterDataModel data) {
        genderAdapter.set(setupGenderDataInit(data.getGenders()));
        view.genderRefresh();

        //categoryAdapter.set(setupCategoryDataInit(data.getCategories()));
        categoryAdapter.set(setupCategoryDataInit(getTestCategry()));
        view.categoryRefresh();

        limitMinPrice = 0;
        limitMaxPrice = 100000;
        int step = (limitMaxPrice / PRICE_STEP) + 1;
        view.initPriceSlider(limitMinPrice, limitMaxPrice, step);

        colorAdapter.set(setupColorDataInit(data.getColors()));
        view.colorRefresh();

        for (int i = 0; i < data.getStyles().size(); i++) {
            StyleDataModel model = data.getStyles().get(i);
            model.setPosition(i);
            model.setSelected(false);
            view.addStyleItem(model);
        }
    }

    private List<GenderDataModel> setupGenderDataInit(List<GenderDataModel> genders) {
        genderCode = genders.get(0).getCode();
        List<GenderDataModel> items = new ArrayList<>();
        for (int i = 0; i < genders.size(); i++) {
            GenderDataModel model = genders.get(i);
            model.setPosition(i);
            model.setSelected(i == 0);
            items.add(model);
        }
        return items;
    }

    private void resetGenderData() {
        List<GenderDataModel> items = genderAdapter.get();
        for (int i = 0; i < items.size(); i++) {
            GenderDataModel model = items.get(i);
            model.setSelected(i == 0);
        }
        view.genderRefresh();
        genderCode = items.get(0).getCode();
    }

    private void resetAgeData() {
        view.resetAgeSlider();
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

    private void resetStyleData() {
        view.resetStyleView();
        styleCodes = new ArrayList<>();
    }







    private List<CategoryDataModel> getTestCategry() {
        List<CategoryDataModel> items = new ArrayList<>();

        CategoryDataModel model1 = new CategoryDataModel();
        model1.setCode("001");
        model1.setName("베이비");
        model1.setImage_url("http://lookbook.minilook.co.kr/data/category/medium/ic_category_baby.png");
        items.add(model1);

        CategoryDataModel model2 = new CategoryDataModel();
        model2.setCode("002");
        model2.setName("상의");
        model2.setImage_url("http://lookbook.minilook.co.kr/data/category/medium/ic_category_top.png");
        items.add(model2);

        CategoryDataModel model3 = new CategoryDataModel();
        model3.setCode("003");
        model3.setName("하의");
        model3.setImage_url("http://lookbook.minilook.co.kr/data/category/medium/ic_category_bottom.png");
        items.add(model3);

        CategoryDataModel model4 = new CategoryDataModel();
        model4.setCode("004");
        model4.setName("원피스");
        model4.setImage_url("http://lookbook.minilook.co.kr/data/category/medium/ic_category_dress.png");
        items.add(model4);

        CategoryDataModel model5 = new CategoryDataModel();
        model5.setCode("005");
        model5.setName("아우터");
        model5.setImage_url("http://lookbook.minilook.co.kr/data/category/medium/ic_category_outer.png");
        items.add(model5);

        CategoryDataModel model6 = new CategoryDataModel();
        model6.setCode("006");
        model6.setName("상하세트");
        model6.setImage_url("http://lookbook.minilook.co.kr/data/category/medium/ic_category_set.png");
        items.add(model6);

        CategoryDataModel model7 = new CategoryDataModel();
        model7.setCode("007");
        model7.setName("실내복/속옷");
        model7.setImage_url("http://lookbook.minilook.co.kr/data/category/medium/ic_category_inner.png");
        items.add(model7);

        CategoryDataModel model9 = new CategoryDataModel();
        model9.setCode("008");
        model9.setName("스포츠/테마");
        model9.setImage_url("http://lookbook.minilook.co.kr/data/category/medium/ic_category_sports.png");
        items.add(model9);

        CategoryDataModel model8 = new CategoryDataModel();
        model8.setCode("009");
        model8.setName("신발");
        model8.setImage_url("http://lookbook.minilook.co.kr/data/category/medium/ic_category_shoes.png");
        items.add(model8);

        CategoryDataModel model10 = new CategoryDataModel();
        model10.setCode("010");
        model10.setName("가방/잡화");
        model10.setImage_url("http://lookbook.minilook.co.kr/data/category/medium/ic_category_accessories.png");
        items.add(model10);

        return items;
    }
}