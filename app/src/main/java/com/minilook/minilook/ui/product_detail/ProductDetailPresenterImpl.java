package com.minilook.minilook.ui.product_detail;

import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.data.network.product.ProductRequest;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.product_detail.di.ProductDetailArguments;
import com.minilook.minilook.util.StringUtil;
import timber.log.Timber;

public class ProductDetailPresenterImpl extends BasePresenterImpl implements ProductDetailPresenter {

    private final View view;
    private final int id;
    private final BaseAdapterDataModel<String> productImageAdapter;
    private final ProductRequest productRequest;

    public ProductDetailPresenterImpl(ProductDetailArguments args) {
        view = args.getView();
        id = args.getId();
        productImageAdapter = args.getProductImageAdapter();
        productRequest = new ProductRequest();
    }

    @Override public void onCreate() {
        view.setupProductImageViewPager();

        reqProductDetail();
    }

    private void reqProductDetail() {
        addDisposable(productRequest.getProductDetail(76)
            .compose(Transformer.applySchedulers())
            .subscribe(this::resProductDetail, Timber::e));
    }

    private void resProductDetail(ProductDataModel data) {
        productImageAdapter.set(data.getImages());
        view.productImageRefresh();

        view.setupBrandName(data.getBrand().getName());
        view.setupProductName(data.getName());

        if (data.is_discount()) {
            view.setupPriceOrigin(StringUtil.toDigit(data.getPrice_origin()));
            view.showPriceOrigin();
            view.setupDiscountPercent(data.getPrice_discount_percent());
            view.showDiscountPercent();
        } else {
            view.hidePriceOrigin();
            view.hideDiscountPercent();
        }
        view.setupPrice(StringUtil.toDigit(data.getPrice()));

        int point = (int)(data.getPrice() * (data.getPoint_percent() / 100f));
        view.setupPoint(point);
        view.setupDeliveryInfoTextView();
    }
}