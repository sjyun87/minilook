package com.minilook.minilook.ui.product_detail;

import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.data.network.product.ProductRequest;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.product_detail.adapter.ProductColorAdapter;
import com.minilook.minilook.ui.product_detail.di.ProductDetailArguments;
import com.minilook.minilook.util.StringUtil;
import timber.log.Timber;

public class ProductDetailPresenterImpl extends BasePresenterImpl implements ProductDetailPresenter {

    private final View view;
    private final int id;
    private final BaseAdapterDataModel<String> productImageAdapter;
    private final BaseAdapterDataModel<ProductDataModel> relatedProductsAdapter;
    private final ProductRequest productRequest;

    private final ProductColorAdapter colorAdapter;

    public ProductDetailPresenterImpl(ProductDetailArguments args) {
        view = args.getView();
        id = args.getId();
        productImageAdapter = args.getProductImageAdapter();
        relatedProductsAdapter = args.getRelatedProductAdapter();
        productRequest = new ProductRequest();

        colorAdapter = args.getColorAdapter();
    }

    @Override public void onCreate() {
        view.setupProductImageViewPager();
        view.setupColorRecyclerView();
        view.setupSizeRecyclerView();
        view.setupTabLayout();
        view.setupWebView();
        view.setupRelatedProductRecyclerView();

        reqProductDetail();
    }

    @Override public void onTabClick(int position) {
        switch (position) {
            case 0:
                view.scrollToProductInfo();
                break;
            case 1:
                view.scrollToReview();
                break;
            case 2:
                view.scrollToQuestion();
                break;
            case 3:
                view.scrollToShippingNRefund();
                break;
        }
    }

    @Override public void onBuyClick() {
        view.showCurtain();
        view.showBuyPanel();
    }

    @Override public void onCurtainClick() {
        view.hideBuyPanel();
    }

    private void reqProductDetail() {
        addDisposable(productRequest.getProductDetail(76)
            .compose(Transformer.applySchedulers())
            .subscribe(this::resProductDetail, Timber::e));
    }

    private void resProductDetail(ProductDataModel data) {
        productImageAdapter.set(data.getImages());
        view.productImageRefresh();

        colorAdapter.set(data.getColors());
        colorAdapter.refresh();


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

        int point = (int) (data.getPrice() * (data.getPoint_percent() / 100f));
        view.setupPoint(point);
        view.setupDeliveryInfoTextView();

        view.setupProductDetail(data.getDetail_url());




        view.setupQuestionCount(StringUtil.toDigit(data.getQuestion_cnt()));

        relatedProductsAdapter.set(data.getRelated_products());
        view.relatedProductRefresh();
    }
}