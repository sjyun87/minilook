package com.minilook.minilook.ui.brand;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.brand.BrandDataModel;
import com.minilook.minilook.data.network.brand.BrandRequest;
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
    private final BaseAdapterDataModel<BrandDataModel> brandAdapter;
    private final BrandRequest brandRequest;

    private Gson gson = new Gson();

    private List<String> selectedStyles = new ArrayList<>();

    public BrandPresenterImpl(BrandArguments args) {
        view = args.getView();
        brandAdapter = args.getBrandAdapter();
        brandRequest = new BrandRequest();
    }

    @Override public void onCreate() {
        view.setupStyleRecyclerView();
        view.setupBrandRecyclerView();

        reqBrands();
    }

    @Override public void resetClick() {
        selectedStyles.clear();
        reqBrands();
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
        brandAdapter.addAll(data);
        view.brandRefresh();
    }
}