package com.minilook.minilook.ui.brand;

import com.minilook.minilook.data.model.brand.BrandDataModel;
import com.minilook.minilook.data.model.brand.BrandInfoDataModel;
import com.minilook.minilook.data.model.brand.StoreInfoDataModel;
import com.minilook.minilook.data.network.brand.BrandRequest;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.brand.di.BrandArguments;
import timber.log.Timber;

public class BrandPresenterImpl extends BasePresenterImpl implements BrandPresenter {

    private final View view;
    private final int brandId;
    private final BrandRequest brandRequest;

    public BrandPresenterImpl(BrandArguments args) {
        view = args.getView();
        brandId = args.getBrandId();
        brandRequest = new BrandRequest();
    }

    @Override public void onCreate() {
        reqPromotion();
    }

    private void reqPromotion() {
        addDisposable(
            brandRequest.getBrand(brandId)
                .compose(Transformer.applySchedulers())
                .subscribe(this::resBrand, Timber::e)
        );
    }

    private void resBrand(BrandDataModel data) {

        BrandInfoDataModel brandInfo = data.getBrand_info();
        StoreInfoDataModel storeInfo = data.getStore_info();

        view.setupBgImage(brandInfo.getImage_url());
        view.setupDesc(brandInfo.getDesc());
        view.setupName(brandInfo.getName());
        view.setupCsDate(storeInfo.getCs_date());
        view.setupCsTel(storeInfo.getCs_tel());
        view.setupCsEmail(storeInfo.getCs_email());
        view.setupCsAddress(storeInfo.getAddress());
    }
}