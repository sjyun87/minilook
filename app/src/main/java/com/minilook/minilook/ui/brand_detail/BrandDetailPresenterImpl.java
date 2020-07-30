package com.minilook.minilook.ui.brand_detail;

import com.minilook.minilook.data.model.brand.BrandDataModel;
import com.minilook.minilook.data.model.brand.BrandInfoDataModel;
import com.minilook.minilook.data.model.brand.StoreInfoDataModel;
import com.minilook.minilook.data.network.brand.BrandRequest;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.brand_detail.di.BrandDetailArguments;
import timber.log.Timber;

public class BrandDetailPresenterImpl extends BasePresenterImpl implements BrandDetailPresenter {

    private final View view;
    private final int id;
    private final BrandRequest brandRequest;

    public BrandDetailPresenterImpl(BrandDetailArguments args) {
        view = args.getView();
        id = args.getBrandId();
        brandRequest = new BrandRequest();
    }

    @Override public void onCreate() {
        reqPromotion();
    }

    private void reqPromotion() {
        addDisposable(
            brandRequest.getBrand(id)
                .compose(Transformer.applySchedulers())
                .subscribe(this::resBrand, Timber::e)
        );
    }

    private void resBrand(BrandDataModel data) {

        //BrandInfoDataModel brandInfo = data.getBrand_info();
        //StoreInfoDataModel storeInfo = data.getStore_info();
        //
        //view.setupBgImage(brandInfo.getUrl_image());
        //view.setupDesc(brandInfo.getDesc());
        //view.setupName(brandInfo.getName());
        //view.setupCsDate(storeInfo.getCs_date());
        //view.setupCsTel(storeInfo.getCs_tel());
        //view.setupCsEmail(storeInfo.getCs_email());
        //view.setupCsAddress(storeInfo.getAddress());fkdgo
    }
}