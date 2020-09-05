package com.minilook.minilook.ui.brand;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.brand.BrandDataModel;
import com.minilook.minilook.data.model.common.StyleDataModel;
import com.minilook.minilook.data.model.search.FilterDataModel;
import com.minilook.minilook.data.network.brand.BrandRequest;
import com.minilook.minilook.data.network.search.SearchRequest;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.brand.di.BrandArguments;
import io.reactivex.rxjava3.functions.Function;
import java.util.ArrayList;
import java.util.List;
import timber.log.Timber;

public class BrandPresenterImpl extends BasePresenterImpl implements BrandPresenter {

    private final View view;
    private final BaseAdapterDataModel<StyleDataModel> styleAdapter;
    private final BaseAdapterDataModel<BrandDataModel> brandAdapter;
    private final SearchRequest searchRequest;
    private final BrandRequest brandRequest;

    private Gson gson = new Gson();

    private List<String> selectedStyles = new ArrayList<>();

    public BrandPresenterImpl(BrandArguments args) {
        view = args.getView();
        styleAdapter = args.getStyleAdapter();
        brandAdapter = args.getBrandAdapter();
        brandRequest = new BrandRequest();
        searchRequest = new SearchRequest();
    }

    @Override public void onCreate() {
        view.setupStyleRecyclerView();
        view.setupBrandRecyclerView();

        reqStyle();
        reqBrands();
    }

    @Override public void resetClick() {
        selectedStyles.clear();
        for (StyleDataModel model :styleAdapter.get()) {
            model.setSelected(false);
        }
        view.styleRefresh();
        view.setupSelectedStyleCount(0, styleAdapter.getSize());

        reqBrands();
    }

    @Override public void onStyleClick(int position) {
        StyleDataModel model = styleAdapter.get(position);
        if (model.isSelected()) {
            model.setSelected(false);
            selectedStyles.remove(model.getCode());
        } else {
            model.setSelected(true);
            selectedStyles.add(model.getCode());
        }
        view.styleRefresh();
        view.setupSelectedStyleCount(selectedStyles.size(), styleAdapter.getSize());
        reqBrands();
    }

    private void reqStyle() {
        addDisposable(searchRequest.getFilterOptions("")
            .compose(Transformer.applySchedulers())
            .filter(data -> data.getCode().equals(HttpCode.OK))
            .map(data -> gson.fromJson(data.getData(), FilterDataModel.class).getStyles())
            .subscribe(this::resStyle, Timber::e));
    }

    private void resStyle(List<StyleDataModel> data) {
        styleAdapter.set(parseToDefault(data));
        view.styleRefresh();

        view.setupSelectedStyleCount(0, styleAdapter.getSize());
    }

    private List<StyleDataModel> parseToDefault(List<StyleDataModel> styles) {
        for (int i = 0; i < styles.size(); i++) {
            styles.get(i).setPosition(i);
            styles.get(i).setSelected(false);
        }
        return styles;
    }

    private void reqBrands() {
        addDisposable(brandRequest.getBrands(selectedStyles)
            .compose(Transformer.applySchedulers())
            .filter(data -> data.getCode().equals(HttpCode.OK))
            .map((Function<BaseDataModel, List<BrandDataModel>>)
                data -> gson.fromJson(data.getData(), new TypeToken<ArrayList<BrandDataModel>>() {
                }.getType()))
            .subscribe(this::resBrands, Timber::e));
    }

    private void resBrands(List<BrandDataModel> data) {
        brandAdapter.set(data);
        view.brandRefresh();
    }
}