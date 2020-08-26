package com.minilook.minilook.ui.search_filter;

import com.google.gson.Gson;
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

    private final SearchRequest searchRequest;

    private Gson gson = new Gson();
    private SearchOptionDataModel options;

    private int genderSelectedPosition = 0;
    private boolean isDiscount = false;
    private boolean isStock = false;

    public SearchFilterPresenterImpl(SearchFilterArguments args) {
        view = args.getView();
        genderAdapter = args.getGenderAdapter();
        searchRequest = new SearchRequest();
        options = new SearchOptionDataModel();
    }

    @Override public void onCreate() {
        view.setupGenderRecyclerView();
        view.setupAgeSlider();

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
}