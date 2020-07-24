package com.minilook.minilook.ui.product_detail;

import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.data.network.product.ProductRequest;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.product_detail.di.ProductDetailArguments;
import timber.log.Timber;

public class ProductDetailPresenterImpl extends BasePresenterImpl implements ProductDetailPresenter {

    private final View view;
    private final int id;
    private final ProductRequest productRequest;

    public ProductDetailPresenterImpl(ProductDetailArguments args) {
        view = args.getView();
        id = args.getId();
        productRequest = new ProductRequest();
    }

    @Override public void onCreate() {
        reqProductDetail();
    }

    private void reqProductDetail() {
        addDisposable(productRequest.getProductDetail(id)
            .compose(Transformer.applySchedulers())
            .subscribe(this::resProductDetail, Timber::e));
    }

    private void resProductDetail(ProductDataModel data) {
        Timber.e(data.toString());

    }
}