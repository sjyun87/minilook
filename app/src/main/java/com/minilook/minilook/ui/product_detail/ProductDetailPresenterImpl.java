package com.minilook.minilook.ui.product_detail;

import com.google.gson.Gson;
import com.minilook.minilook.data.model.product.ProductColorDataModel;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.data.model.product.ProductStockModel;
import com.minilook.minilook.data.network.product.ProductRequest;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.product_detail.di.ProductDetailArguments;
import com.minilook.minilook.util.StringUtil;
import java.util.ArrayList;
import java.util.List;
import timber.log.Timber;

public class ProductDetailPresenterImpl extends BasePresenterImpl implements ProductDetailPresenter {

    private static final String STOCK_TYPE_SIZE = "size";
    private static final String STOCK_TYPE_COLOR = "color";

    private final View view;
    private final int id;
    private final BaseAdapterDataModel<String> productImageAdapter;
    private final BaseAdapterDataModel<ProductDataModel> relatedProductsAdapter;
    private final ProductRequest productRequest;

    private Gson gson = new Gson();
    private ProductDataModel data;

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
        //reqProductOptions();
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

    @Override public void onBrandClick() {
        view.navigateToBrandDetail(data.getBrand_id());
    }

    private void reqProductDetail() {
        addDisposable(productRequest.getProductDetail(id)
            .map(data -> gson.fromJson(data.getData(), ProductDataModel.class))
            .compose(Transformer.applySchedulers())
            .subscribe(this::resProductDetail, Timber::e));
    }

    private void resProductDetail(ProductDataModel data) {
        this.data = data;
        productImageAdapter.set(checkValid(data.getProduct_images()));
        view.productImageRefresh();

        view.setupBrandName(data.getBrand_name());
        view.setupProductName(data.getProduct_name());

        for (ProductStockModel model : data.getProductStocks()) {
            if (model.getType().equals(STOCK_TYPE_COLOR)) {
                view.addColorView(model);
            } else if (model.getType().equals(STOCK_TYPE_SIZE)) {
                view.addSizeView(model);
            }
        }

        if (data.isDiscount()) {
            view.setupPriceOrigin(StringUtil.toDigit(data.getPrice_origin()));
            view.showPriceOrigin();
            view.setupDiscountPercent(data.getDiscount_percent());
            view.showDiscountPercent();
        } else {
            view.hidePriceOrigin();
            view.hideDiscountPercent();
        }
        view.setupPrice(StringUtil.toDigit(data.getPrice()));

        view.setupPoint(data.getPoint());
        view.setupDeliveryInfoTextView();

        view.setupProductDetail(data.getDetail_url());

        view.setupReviewCount(StringUtil.toDigit(data.getReview_cnt()));
        // 리뷰 화면 들어가야함

        view.setupQuestionCount(StringUtil.toDigit(data.getQuestion_cnt()));

        List<ProductDataModel> relatedProducts = data.getRelated_products();
        if (relatedProducts != null) {
            relatedProductsAdapter.set(data.getRelated_products());
            view.relatedProductRefresh();
            view.showRelatedPanel();
        } else {
            view.hideRelatedPanel();
        }
    }

    private List<String> checkValid(List<String> images) {
        List<String> items = new ArrayList<>();
        for (String url : images) {
            if (url != null && !url.equals("")) items.add(url);
        }
        return items;
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