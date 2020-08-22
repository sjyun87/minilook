package com.minilook.minilook.ui.product_detail;

import com.minilook.minilook.data.model.product.ProductColorDataModel;
import com.minilook.minilook.data.model.product.ProductSizeDataModel;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.data.network.product.ProductRequest;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.product_detail.di.ProductDetailArguments;
import com.minilook.minilook.util.StringUtil;
import java.util.List;
import timber.log.Timber;

public class ProductDetailPresenterImpl extends BasePresenterImpl implements ProductDetailPresenter {

    private final View view;
    private final int id;
    private final BaseAdapterDataModel<String> productImageAdapter;
    private final BaseAdapterDataModel<ProductDataModel> relatedProductsAdapter;
    private final ProductRequest productRequest;

    public ProductDetailPresenterImpl(ProductDetailArguments args) {
        view = args.getView();
        id = args.getId();
        productImageAdapter = args.getProductImageAdapter();
        relatedProductsAdapter = args.getRelatedProductAdapter();
        productRequest = new ProductRequest();
    }

    @Override public void onCreate() {
        view.setupProductImageViewPager();
        view.setupTabLayout();
        view.setupWebView();
        view.setupRelatedProductRecyclerView();

        reqProductDetail();
        reqProductOptions();
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
        view.showOptionSelector();
    }

    private void reqProductDetail() {
        addDisposable(productRequest.getProductDetail(id)
            .compose(Transformer.applySchedulers())
            .subscribe(this::resProductDetail, Timber::e));
    }

    private void resProductDetail(ProductDataModel data) {
        // Product Image List
        productImageAdapter.set(data.getImages());
        view.productImageRefresh();

        //view.setupBrandName(data.getBrand().getName());
        view.setupProductName(data.getName());

        // Option
        for (ProductColorDataModel productColorDataModel : data.getColors()) {
            view.addColorView(productColorDataModel);
        }

        for (ProductSizeDataModel productSizeDataModel : data.getSizes()) {
            view.addSizeView(productSizeDataModel);
        }

        // Price
        if (data.isDiscount()) {
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

        // Product Detail
        view.setupProductDetail(data.getDetail_url());

        view.setupReviewCount(StringUtil.toDigit(data.getReview_cnt()));
        // 리뷰 화면 들어가야함

        view.setupQuestionCount(StringUtil.toDigit(data.getQuestion_cnt()));

        // Related Product List
        relatedProductsAdapter.set(data.getRelated_products());
        view.relatedProductRefresh();
    }

    private void reqProductOptions() {
        addDisposable(productRequest.getProductOptions(76)
            .compose(Transformer.applySchedulers())
            .subscribe(this::resProductOptions, Timber::e));
    }

    private void resProductOptions(List<ProductColorDataModel> data) {
        view.setupOptionSelector(data);
    }
}