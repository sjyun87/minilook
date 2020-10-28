package com.minilook.minilook.ui.preorder_product_detail;

import com.google.gson.Gson;
import com.minilook.minilook.data.code.StockType;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.data.model.product.ProductStockDataModel;
import com.minilook.minilook.data.network.product.ProductRequest;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.preorder_product_detail.di.PreorderProductDetailArguments;
import com.minilook.minilook.util.StringUtil;
import java.util.ArrayList;
import java.util.List;
import timber.log.Timber;

public class PreorderProductDetailPresenterImpl extends BasePresenterImpl implements PreorderProductDetailPresenter {

    private final View view;
    private final String title;
    private final int preorderNo;
    private final int productNo;
    private final BaseAdapterDataModel<String> imageAdapter;
    private final ProductRequest productRequest;

    private Gson gson = new Gson();
    private boolean isInfoPanelExpanded = false;

    public PreorderProductDetailPresenterImpl(PreorderProductDetailArguments args) {
        view = args.getView();
        title = args.getTitle();
        preorderNo = args.getPreorderNo();
        productNo = args.getProductNo();
        imageAdapter = args.getImageAdapter();
        productRequest = new ProductRequest();
    }

    @Override public void onCreate() {
        view.setTitle(title);
        view.setupViewPager();
        view.setupWebView();

        reqPreorderProductDetail();
    }

    @Override public void onExpandClick() {
        if (isInfoPanelExpanded) {
            view.collapseInfoMorePanel();
        } else {
            view.expandInfoMorePanel();
        }
        isInfoPanelExpanded = !isInfoPanelExpanded;
    }

    private void reqPreorderProductDetail() {
        addDisposable(productRequest.getProductDetail(productNo, preorderNo)
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                String code = data.getCode();
                return code.equals(HttpCode.OK);
            })
            .map(data -> gson.fromJson(data.getData(), ProductDataModel.class))
            .subscribe(this::resPreorderProductDetail, Timber::e));
    }

    private void resPreorderProductDetail(ProductDataModel data) {
        imageAdapter.set(checkValid(data.getImages()));
        view.imageRefresh();

        view.setProductIndex(title);
        view.setProductName(data.getProductName());

        if (data.getStocks() != null) {
            for (ProductStockDataModel model : data.getStocks()) {
                switch (StockType.toType(model.getType())) {
                    case SIZE:
                        view.addSizeView(model);
                        break;
                    case COLOR:
                        view.addColorView(model);
                        break;
                }
            }
        }

        if (data.isDiscount()) {
            view.setupPriceOrigin(StringUtil.toDigit(data.getPriceOrigin()));
            view.showPriceOrigin();
            view.setupDiscountPercent(data.getDiscountPercent());
            view.showDiscountPercent();
        } else {
            view.hidePriceOrigin();
            view.hideDiscountPercent();
        }
        view.setupPrice(StringUtil.toDigit(data.getPrice()));

        view.setupProductDetail(data.getDetailUrl());

        view.setupInfoStyleNo(data.getInfoStyleNo());
        view.setupInfoKcAuth(data.getInfoKCAuth());
        view.setupInfoWeight(data.getInfoWeight());
        view.setupInfoColor(data.getInfoColor());
        view.setupInfoMaterial(data.getInfoMaterial());
        view.setupInfoAge(data.getInfoAge());
        view.setupInfoReleaseDate(data.getInfoReleaseDate());
        view.setupInfoManufacturer(data.getInfoManufacturer());
        view.setupInfoCountry(data.getInfoCountry());
        view.setupInfoCaution(data.getInfoCaution());
        view.setupInfoWarranty(data.getInfoWarranty());
        view.setupInfoDamage(data.getInfoDamage());
        view.setupInfoServiceCenter(data.getInfoServiceCenter());
    }

    private List<String> checkValid(List<String> images) {
        List<String> items = new ArrayList<>();
        for (String url : images) {
            if (url != null && !url.equals("")) items.add(url);
        }
        return items;
    }
}