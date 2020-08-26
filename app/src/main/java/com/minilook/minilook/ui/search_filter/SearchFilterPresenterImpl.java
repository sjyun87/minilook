package com.minilook.minilook.ui.search_filter;

import com.google.gson.Gson;
import com.minilook.minilook.data.model.common.CategoryDataModel;
import com.minilook.minilook.data.model.common.GenderDataModel;
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

    private final View view;
    private final BaseAdapterDataModel<GenderDataModel> genderAdapter;
    private final BaseAdapterDataModel<CategoryDataModel> categoryAdapter;

    private final SearchRequest searchRequest;

    private Gson gson = new Gson();
    private SearchOptionDataModel options;

    private int genderSelectedPosition = 0;
    private boolean isDiscount = false;
    private boolean isStock = false;
    private int categorySelectedPosition = -1;

    public SearchFilterPresenterImpl(SearchFilterArguments args) {
        view = args.getView();
        genderAdapter = args.getGenderAdapter();
        categoryAdapter = args.getCategoryAdapter();
        searchRequest = new SearchRequest();
        options = new SearchOptionDataModel();
    }

    @Override public void onCreate() {
        view.setupGenderRecyclerView();
        view.setupAgeSlider();
        view.setupCategoryRecyclerView();

        reqFilterOptions();
    }

    @Override public void onGenderSelected(GenderDataModel data) {
        if (genderSelectedPosition != data.getPosition()) {
            options.setGender_code(data.getCode());
            genderAdapter.get(genderSelectedPosition).setSelected(false);
            genderSelectedPosition = data.getPosition();
            genderAdapter.get(genderSelectedPosition).setSelected(true);
            view.genderRefresh();
        }
    }

    @Override public void onAgeChanged(float value) {
        int age = (int) value;
        int optionAge;
        if (age <= 24) {    // 0 ~ 24개월
            view.setupAge(age, true);
            optionAge = age;
        } else {    // 3 ~ 16세
            view.setupAge(age - 22, false);
            optionAge = ((age - 22) * 12) - 6;
        }
        options.setAge(optionAge);
    }

    @Override public void onAttributeDiscountClick() {
        if (isDiscount) {
            view.setupSelectedDiscount();
        } else {
            view.setupUnselectedDiscount();
        }
        isDiscount = !isDiscount;
        options.setDiscount(isDiscount);
    }

    @Override public void onAttributeStockClick() {
        if (isStock) {
            view.setupSelectedStock();
        } else {
            view.setupUnselectedStock();
        }
        isStock = !isStock;
        options.setStock(isStock);
    }

    @Override public void onCategorySelected(CategoryDataModel data) {
        if (categorySelectedPosition != data.getPosition()) {
            if (categorySelectedPosition != -1) {
                categoryAdapter.get(categorySelectedPosition).setSelected(false);
            }
            categorySelectedPosition = data.getPosition();
            categoryAdapter.get(categorySelectedPosition).setSelected(true);
            view.categoryRefresh();
            options.setCategory_code(data.getCode());
        } else {
            categoryAdapter.get(categorySelectedPosition).setSelected(false);
            categorySelectedPosition = -1;
            options.setCategory_code("");
            view.categoryRefresh();
        }
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
        genderAdapter.set(setupGenderInit(data.getGenders()));
        view.genderRefresh();




        //categoryAdapter.set(setupCategoryInit(data.getCategories()));
        categoryAdapter.set(setupCategoryInit(getTestCategry()));
        view.categoryRefresh();



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

    private List<GenderDataModel> setupGenderInit(List<GenderDataModel> genders) {
        options.setGender_code(genders.get(0).getCode());
        List<GenderDataModel> items = new ArrayList<>();
        for (int i = 0; i < genders.size(); i++) {
            GenderDataModel model = genders.get(i);
            model.setPosition(i);
            model.setSelected(i == 0);
            items.add(model);
        }
        return items;
    }

    private List<CategoryDataModel> setupCategoryInit(List<CategoryDataModel> categories) {
        List<CategoryDataModel> items = new ArrayList<>();
        for (int i = 0; i < categories.size(); i++) {
            CategoryDataModel model = categories.get(i);
            model.setPosition(i);
            model.setSelected(false);
            items.add(model);
        }
        return items;
    }
}