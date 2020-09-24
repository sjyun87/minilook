package com.minilook.minilook.ui.brand_info;

import android.text.TextUtils;
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
    private final int brandNo;
    private final BrandRequest brandRequest;

    private Gson gson = new Gson();

    public BrandInfoPresenterImpl(BrandInfoArguments args) {
        view = args.getView();
        brandNo = args.getBrandNo();
        brandRequest = new BrandRequest();
    }

    @Override public void onCreate() {
        reqBrandInfo();
    }

    private void reqBrandInfo() {
        addDisposable(
            brandRequest.getBrandInfo(brandNo)
                .compose(Transformer.applySchedulers())
                .filter(data -> data.getCode().equals(HttpCode.OK))
                .map(data -> gson.fromJson(data.getData(), BrandInfoDataModel.class))
                .subscribe(this::resBrandInfo, Timber::e)
        );
    }

    private void resBrandInfo(BrandInfoDataModel data) {
        view.setupCStime(data.getCs_time());
        view.setupCStel(data.getCs_tel());
        String csSNS = data.getCs_sns();
        if (TextUtils.isEmpty(csSNS)) {
            view.hideCSsnsPanel();
        } else {
            view.setupCSsns(csSNS);
        }
        view.setupCSemail(data.getCs_email());
        view.setupGuide(data.getGuide());
    }
}