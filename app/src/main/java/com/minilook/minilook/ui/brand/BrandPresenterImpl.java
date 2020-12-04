package com.minilook.minilook.ui.brand;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.minilook.minilook.App;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.brand.BrandDataModel;
import com.minilook.minilook.data.model.common.CodeDataModel;
import com.minilook.minilook.data.model.search.FilterDataModel;
import com.minilook.minilook.data.network.brand.BrandRequest;
import com.minilook.minilook.data.network.search.SearchRequest;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.brand.di.BrandArguments;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Function;
import java.util.ArrayList;
import java.util.List;
import timber.log.Timber;

public class BrandPresenterImpl extends BasePresenterImpl implements BrandPresenter {

    private final View view;
    private final BaseAdapterDataModel<CodeDataModel> styleAdapter;
    private final BaseAdapterDataModel<BrandDataModel> brandAdapter;
    private final SearchRequest searchRequest;
    private final BrandRequest brandRequest;
    private final Gson gson;

    private final List<String> selectedStyles = new ArrayList<>();

    public BrandPresenterImpl(BrandArguments args) {
        view = args.getView();
        styleAdapter = args.getStyleAdapter();
        brandAdapter = args.getBrandAdapter();
        brandRequest = new BrandRequest();
        searchRequest = new SearchRequest();
        gson = App.getInstance().getGson();
    }

    @Override public void onCreate() {
        view.setupClickAction();
        view.setupStyleRecyclerView();
        view.setupBrandRecyclerView();

        getStyles();
        getBrands();
    }

    @Override public void onBrandScrap(BrandDataModel data) {
        for (BrandDataModel brand : brandAdapter.get()) {
            if (brand.getBrandNo() == data.getBrandNo()) {
                brand.setScrap(data.isScrap());
                brand.setScrapCount(data.getScrapCount());
                view.brandRefresh();
            }
        }
    }

    @Override public void onResetClick() {
        selectedStyles.clear();
        initData(styleAdapter.get());
        view.styleRefresh();
        view.setupSelectedStyleCount(0, styleAdapter.getSize());

        getBrands();
    }

    @Override public void onStyleClick(CodeDataModel data) {
        if (data.isSelected()) {
            data.setSelected(false);
            selectedStyles.remove(data.getCode());
        } else {
            data.setSelected(true);
            selectedStyles.add(data.getCode());
        }
        view.styleRefresh();
        view.setupSelectedStyleCount(selectedStyles.size(), styleAdapter.getSize());

        getBrands();
    }

    private void getStyles() {
        addDisposable(searchRequest.getFilterOptions()
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                String code = data.getCode();
                return code.equals(HttpCode.OK);
            })
            .map(data -> gson.fromJson(data.getData(), FilterDataModel.class).getStyles())
            .subscribe(this::onResStyles, Timber::e));
    }

    private void onResStyles(List<CodeDataModel> data) {
        styleAdapter.set(initData(data));
        view.styleRefresh();
        view.setupSelectedStyleCount(0, styleAdapter.getSize());
    }

    private List<CodeDataModel> initData(List<CodeDataModel> items) {
        return Observable.fromIterable(items)
            .map(model -> {
                model.setSelected(false);
                return model;
            })
            .toList()
            .blockingGet();
    }

    private void getBrands() {
        addDisposable(brandRequest.getBrands(selectedStyles)
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                String code = data.getCode();
                if (code.equals(HttpCode.NO_DATA)) {
                    view.showEmptyPanel();
                }
                return code.equals(HttpCode.OK);
            })
            .map((Function<BaseDataModel, List<BrandDataModel>>)
                data -> gson.fromJson(data.getData(), new TypeToken<ArrayList<BrandDataModel>>() {
                }.getType()))
            .subscribe(this::onResBrands, Timber::e));
    }

    private void onResBrands(List<BrandDataModel> data) {
        brandAdapter.set(data);
        view.brandRefresh();
        view.hideEmptyPanel();
    }
}