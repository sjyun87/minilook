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

    public SearchFilterPresenterImpl(SearchFilterArguments args) {
        view = args.getView();
        genderAdapter = args.getGenderAdapter();
        searchRequest = new SearchRequest();
        options = new SearchOptionDataModel();
    }

    @Override public void onCreate() {
        view.genderRecyclerView();

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