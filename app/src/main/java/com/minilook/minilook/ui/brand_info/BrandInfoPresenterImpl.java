package com.minilook.minilook.ui.brand_info;

import android.text.TextUtils;
import com.google.gson.Gson;
import com.minilook.minilook.App;
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
    private final Gson gson;

    public BrandInfoPresenterImpl(BrandInfoArguments args) {
        view = args.getView();
        brandNo = args.getBrandNo();
        brandRequest = new BrandRequest();
        gson = App.getInstance().getGson();
    }

    @Override public void onCreate() {
        getBrandInfo();
    }

    private void getBrandInfo() {
        addDisposable(
            brandRequest.getBrandInfo(brandNo)
                .compose(Transformer.applySchedulers())
                .filter(data -> {
                    String code = data.getCode();
                    if (!code.equals(HttpCode.OK)) {
                        view.showErrorDialog();
                    }
                    return code.equals(HttpCode.OK);
                })
                .map(data -> gson.fromJson(data.getData(), BrandInfoDataModel.class))
                .subscribe(this::onResBrandInfo, Timber::e)
        );
    }

    private void onResBrandInfo(BrandInfoDataModel data) {
        view.setCStime(data.getCsTime());
        view.setCStel(data.getCsTel());
        String csSNS = data.getCsSNS();
        if (TextUtils.isEmpty(csSNS)) {
            view.hideCSsnsPanel();
        } else {
            view.setCSsns(csSNS);
        }
        view.setCSemail(data.getCsEmail());
        view.setGuide(data.getGuide());
    }
}