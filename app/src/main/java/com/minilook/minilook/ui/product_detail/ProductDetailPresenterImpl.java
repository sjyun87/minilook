package com.minilook.minilook.ui.product_detail;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.order.OrderOptionDataModel;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.data.model.product.ProductOptionDataModel;
import com.minilook.minilook.data.model.product.ProductStockModel;
import com.minilook.minilook.data.model.review.ReviewDataModel;
import com.minilook.minilook.data.network.order.OrderRequest;
import com.minilook.minilook.data.network.product.ProductRequest;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.data.type.DisplayCode;
import com.minilook.minilook.data.type.ShippingType;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.product_detail.di.ProductDetailArguments;
import com.minilook.minilook.util.StringUtil;
import io.reactivex.rxjava3.functions.Function;
import java.util.ArrayList;
import java.util.List;
import timber.log.Timber;

public class ProductDetailPresenterImpl extends BasePresenterImpl implements ProductDetailPresenter {

    private static final String STOCK_TYPE_SIZE = "size";
    private static final String STOCK_TYPE_COLOR = "color";

    private final View view;
    private final int id;
    private final BaseAdapterDataModel<String> productImageAdapter;
    private final BaseAdapterDataModel<ReviewDataModel> reviewAdapter;
    private final BaseAdapterDataModel<ProductDataModel> relatedProductsAdapter;
    private final ProductRequest productRequest;
    private final OrderRequest orderRequest;

    private Gson gson = new Gson();
    private ProductDataModel data;
    private boolean isInfoPanelExpanded = false;

    public ProductDetailPresenterImpl(ProductDetailArguments args) {
        view = args.getView();
        id = args.getId();
        productImageAdapter = args.getProductImageAdapter();
        reviewAdapter = args.getReviewAdapter();
        relatedProductsAdapter = args.getRelatedProductAdapter();
        productRequest = new ProductRequest();
        orderRequest = new OrderRequest();
    }

    @Override public void onCreate() {
        view.setupProductImageViewPager();
        view.setupTabLayout();
        view.setupWebView();
        view.setupReviewRecyclerView();
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
        view.showOptionSelector();
    }

    @Override public void onBrandClick() {
        view.navigateToBrandDetail(data.getBrand_id());
    }

    @Override public void onExpandClick() {
        if (isInfoPanelExpanded) {
            view.collapseInfoMorePanel();
        } else {
            view.expandInfoMorePanel();
        }
        isInfoPanelExpanded = !isInfoPanelExpanded;
    }

    @Override public void onShoppingBagClick(List<OrderOptionDataModel> goodsData) {
        reqAddShoppingBag(goodsData);
        view.hideOptionSelector();
    }

    private void reqAddShoppingBag(List<OrderOptionDataModel> goodsData) {
        addDisposable(orderRequest.addShoppingBag(goodsData)
            .compose(Transformer.applySchedulers())
            .subscribe(this::resAddShoppingBag, Timber::e));
    }

    private void resAddShoppingBag(BaseDataModel data) {
        Timber.e(data.toString());
        view.navigateToShoppingBag();
    }

    private void reqProductDetail() {
        addDisposable(productRequest.getProductDetail(id)
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                return data.getCode().equals(HttpCode.OK);
            })
            .map(data -> gson.fromJson(data.getData(), ProductDataModel.class))
            .subscribe(this::resProductDetail, Timber::e));
    }

    private void resProductDetail(ProductDataModel data) {
        this.data = data;
        reqProductOptions();

        productImageAdapter.set(checkValid(data.getProduct_images()));
        view.productImageRefresh();

        view.setupBrandName(data.getBrand_name());
        view.setupProductName(data.getProduct_name());

        if (data.getProductStocks() != null) {
            for (ProductStockModel model : data.getProductStocks()) {
                if (model.getType().equals(STOCK_TYPE_COLOR)) {
                    view.addColorView(model);
                } else if (model.getType().equals(STOCK_TYPE_SIZE)) {
                    view.addSizeView(model);
                }
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

        view.setupPoint((int) (data.getPrice() * (data.getPoint() / 100f)));

        int shippingType = data.getShipping_type();
        if (shippingType == ShippingType.FREE.getValue()){
            view.setupShippingFree();
            view.hideShippingCondition();
        } else if (shippingType == ShippingType.PAID.getValue()) {
            view.setupShippingPrice(data.getShipping_price());
            view.hideShippingCondition();
        } else if (shippingType == ShippingType.CONDITIONAL.getValue()) {
            view.setupShippingPrice(data.getBasic_shipping_price());
            view.setupShippingCondition(data.getFree_shipping_condition());
            view.showShippingCondition();
        }

        view.setupProductDetail(data.getDetail_url());

        view.setupInfoStyleNo(data.getInfo_style_no());
        view.setupInfoKcAuth(data.getInfo_kc_auth());
        view.setupInfoWeight(data.getInfo_weight());
        view.setupInfoColor(data.getInfo_color());
        view.setupInfoMaterial(data.getInfo_material());
        view.setupInfoAge(data.getInfo_age());
        view.setupInfoReleaseDate(data.getInfo_release_date());
        view.setupInfoManufacturer(data.getInfo_manufacturer());
        view.setupInfoCountry(data.getInfo_country());
        view.setupInfoCaution(data.getInfo_caution());
        view.setupInfoWarranty(data.getInfo_warranty());
        view.setupInfoDamage(data.getInfo_damage());
        view.setupInfoServiceCenter(data.getInfo_service_center());

        view.setupReviewCount(StringUtil.toDigit(data.getReview_cnt()));

        List<ReviewDataModel> reviews = data.getReviews();
        if (reviews != null) {
            reviewAdapter.set(data.getReviews());
            view.reviewRefresh();
            view.showReviewContentsPanel();
        }

        view.setupQuestionCount(StringUtil.toDigit(data.getQuestion_cnt()));

        List<ProductDataModel> relatedProducts = data.getRelated_products();
        if (relatedProducts != null) {
            relatedProductsAdapter.set(data.getRelated_products());
            view.relatedProductRefresh();
            view.showRelatedPanel();
        } else {
            view.hideRelatedPanel();
        }

        int displayCode = data.getDisplay_code();
        if (displayCode != DisplayCode.DISPLAY.getValue()) {
            view.showDisplayLabel(data.getDisplay_label());
            view.disableBuyButton(data.getDisplay_label());
            view.setupPriceOriginNoDisplayColor();
            view.setupDiscountPercentNoDisplayColor();
            view.setupPriceNoDisplayColor();
            if (displayCode == DisplayCode.STOP_SELLING.getValue()) view.hideScrap();
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
        addDisposable(productRequest.getProductOptions(id)
            .compose(Transformer.applySchedulers())
            .filter(data -> data.getCode().equals(HttpCode.OK))
            .map((Function<BaseDataModel, List<ProductOptionDataModel>>)
                data -> gson.fromJson(data.getData(), new TypeToken<ArrayList<ProductOptionDataModel>>() {
                }.getType()))
            .subscribe(this::resProductOptions, Timber::e));
    }

    private void resProductOptions(List<ProductOptionDataModel> options) {
        view.setupOptionSelector(data.getPrice(), options);
    }
}