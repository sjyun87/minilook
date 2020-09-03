package com.minilook.minilook.ui.brand_info;

import com.google.gson.Gson;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.model.brand.BrandInfoDataModel;
import com.minilook.minilook.data.network.brand.BrandRequest;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.brand_info.di.BrandInfoArguments;
import timber.log.Timber;

public class BrandInfoPresenterImpl extends BasePresenterImpl implements BrandInfoPresenter {

    private final View view;
    private final int brand_id;
    private final BrandRequest brandRequest;

    private Gson gson = new Gson();

    public BrandInfoPresenterImpl(BrandInfoArguments args) {
        view = args.getView();
        brand_id = args.getBrand_id();
        brandRequest = new BrandRequest();
    }

    @Override public void onCreate() {
        reqBrandInfo();
    }

    private void reqBrandInfo() {
        addDisposable(
            brandRequest.getBrandInfo(brand_id)
                .compose(Transformer.applySchedulers())
                .filter(data -> data.getCode().equals(HttpCode.OK))
                .map(data -> gson.fromJson(data.getData(), BrandInfoDataModel.class))
                .subscribe(this::resBrandInfo, Timber::e)
        );
    }

    private void resBrandInfo(BrandInfoDataModel data) {
        view.setupCStime(data.getCs_time());
        view.setupCStel(data.getCs_tel());
        String cs_sns = data.getCs_sns();
        if (cs_sns == null || cs_sns.equals("")) {
            view.hideCSsnsPanel();
        } else {
            view.setupCSsns(data.getCs_sns());
        }
        view.setupCSemail(data.getCs_email());
        view.setupGuide(data.getGuide());
    }
}