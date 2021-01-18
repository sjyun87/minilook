package com.minilook.minilook.ui.product_detail;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.minilook.minilook.App;
import com.minilook.minilook.data.code.DisplayCode;
import com.minilook.minilook.data.code.ShippingCode;
import com.minilook.minilook.data.code.StockType;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.product.OptionColorDataModel;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.data.model.product.ProductStockDataModel;
import com.minilook.minilook.data.model.review.ReviewDataModel;
import com.minilook.minilook.data.model.shopping.ShoppingBrandDataModel;
import com.minilook.minilook.data.model.shopping.ShoppingOptionDataModel;
import com.minilook.minilook.data.model.shopping.ShoppingProductDataModel;
import com.minilook.minilook.data.network.order.OrderRequest;
import com.minilook.minilook.data.network.product.ProductRequest;
import com.minilook.minilook.data.network.review.ReviewRequest;
import com.minilook.minilook.data.network.scrap.ScrapRequest;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.main.MainPresenterImpl;
import com.minilook.minilook.ui.product_detail.di.ProductDetailArguments;
import com.minilook.minilook.ui.question_write.QuestionWritePresenterImpl;
import com.minilook.minilook.ui.review.ReviewPresenterImpl;
import com.minilook.minilook.util.DynamicLinkUtil;
import com.minilook.minilook.util.StringUtil;
import com.minilook.minilook.util.TrackingUtil;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Function;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import timber.log.Timber;

public class ProductDetailPresenterImpl extends BasePresenterImpl implements ProductDetailPresenter {

    private final View view;
    private final int productNo;
    private final BaseAdapterDataModel<String> productImageAdapter;
    private final BaseAdapterDataModel<ReviewDataModel> reviewAdapter;
    private final BaseAdapterDataModel<ProductDataModel> relatedProductsAdapter;
    private final ProductRequest productRequest;
    private final OrderRequest orderRequest;
    private final ScrapRequest scrapRequest;
    private final ReviewRequest reviewRequest;
    private final DynamicLinkUtil dynamicLinkUtil;

    private Gson gson = new Gson();
    private ProductDataModel data;
    private boolean isInfoPanelExpanded = false;

    public ProductDetailPresenterImpl(ProductDetailArguments args) {
        view = args.getView();
        productNo = args.getProductNo();
        productImageAdapter = args.getProductImageAdapter();
        reviewAdapter = args.getReviewAdapter();
        relatedProductsAdapter = args.getRelatedProductAdapter();
        productRequest = new ProductRequest();
        orderRequest = new OrderRequest();
        scrapRequest = new ScrapRequest();
        reviewRequest = new ReviewRequest();
        dynamicLinkUtil = new DynamicLinkUtil();
    }

    @Override public void onCreate() {
        toRxObservable();
        view.setupProductImageViewPager();
        view.setupTabLayout();
        view.setupWebView();
        view.setupReviewRecyclerView();
        view.setupRelatedProductRecyclerView();

        reqProductDetail();
    }

    @Override public void onResume() {
        TrackingUtil.pageTracking("상품 상세페이지", ProductDetailActivity.class.getSimpleName());
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

    @Override public void onReviewMoreClick() {
        view.navigateToReview(productNo);
    }

    @Override public void onQuestionClick() {
        view.navigateToQuestion(productNo);
    }

    @Override public void onScrapClick() {
        if (App.getInstance().isLogin()) {
            data.setScrap(!data.isScrap());
            if (data.isScrap()) {
                data.setScrapCount(data.getScrapCount() + 1);
            } else {
                data.setScrapCount(data.getScrapCount() - 1);
            }
            setupScrap();
            RxBus.send(new MainPresenterImpl.RxBusEventUpdateProductScrap(data));
        } else {
            view.navigateToLogin();
        }
    }

    @Override public void onBuyClick() {
        if (App.getInstance().isLogin()) {
            view.showOptionSelector();
            reqProductOptions();
        } else {
            view.navigateToLogin();
        }
    }

    @Override public void onBrandClick() {
        view.navigateToBrandDetail(data.getBrandNo());
    }

    @Override public void onExpandClick() {
        if (isInfoPanelExpanded) {
            view.collapseInfoMorePanel();
        } else {
            view.expandInfoMorePanel();
        }
        isInfoPanelExpanded = !isInfoPanelExpanded;
    }

    @Override public void onShippingNRefundClick() {
        view.navigateToProductInfo(data.getBrandNo());
    }

    @Override public void onOptionSelectorShoppingBagClick(List<ShoppingOptionDataModel> optionData) {
        reqAddShoppingBag(optionData);
    }

    @Override public void onOptionSelectorBuyClick(List<ShoppingOptionDataModel> optionData) {
        List<ShoppingBrandDataModel> brandData = parseToData(optionData);
        view.navigateToOrder(brandData);
    }

    private List<ShoppingBrandDataModel> parseToData(List<ShoppingOptionDataModel> optionData) {
        List<ShoppingBrandDataModel> brandData = new ArrayList<>();
        ShoppingBrandDataModel brandModel = new ShoppingBrandDataModel();
        brandModel.setBrandNo(data.getBrandNo());
        brandModel.setBrandName(data.getBrandName());
        brandModel.setBrandLogo(data.getBrandLogo());
        brandModel.setShippingType(data.getShippingType());
        brandModel.setShippingPrice(data.getShippingPrice());
        brandModel.setConditionShippingPrice(data.getConditionShippingPrice());
        brandModel.setConditionFreeShipping(data.getConditionFreeShipping());

        List<ShoppingProductDataModel> productData = new ArrayList<>();
        ShoppingProductDataModel productModel = new ShoppingProductDataModel();
        productModel.setDisplayCode(data.getDisplayCode());
        productModel.setProductNo(data.getProductNo());
        productModel.setProductName(data.getProductName());
        productModel.setThumbUrl(data.getImages().get(0));
        productModel.setDiscount(data.isDiscount());
        productModel.setDiscountPercent(data.getDiscountPercent());
        productModel.setPrice(data.getPrice());
        productModel.setAddPointPercent(data.getPoint());
        productModel.setOptions(optionData);
        productData.add(productModel);
        brandModel.setProducts(productData);
        brandData.add(brandModel);
        return brandData;
    }

    @Override public void onShareClick() {
        String title = data.getProductName() + " - " + data.getBrandName();
        dynamicLinkUtil.createLink(DynamicLinkUtil.TYPE_PRODUCT, productNo, title, data.getImages().get(0),
            new DynamicLinkUtil.OnDynamicLinkListener() {
                @Override public void onSuccess(String link) {
                    view.sendDynamicLink(link);
                }

                @Override public void onError() {
                    view.showErrorDialog();
                }
            });
    }

    private void reqAddShoppingBag(List<ShoppingOptionDataModel> optionData) {
        addDisposable(orderRequest.addShoppingBag(optionData)
            .compose(Transformer.applySchedulers())
            .subscribe(this::resAddShoppingBag, Timber::e));
    }

    private void resAddShoppingBag(BaseDataModel data) {
        view.showAddShoppingBagToast();
    }

    private void reqProductDetail() {
        addDisposable(productRequest.getProductDetail(productNo)
            .compose(Transformer.applySchedulers())
            .filter(data -> data.getCode().equals(HttpCode.OK))
            .map(data -> gson.fromJson(data.getData(), ProductDataModel.class))
            .subscribe(this::resProductDetail, Timber::e));
    }

    private void resProductDetail(ProductDataModel data) {
        this.data = data;

        productImageAdapter.set(checkValid(data.getImages()));
        view.productImageRefresh();

        view.setupBrandName(data.getBrandName());
        view.setupProductName(data.getProductName());

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

        view.setupPoint((int) (data.getPrice() * (data.getPoint() / 100f)));

        int shippingType = data.getShippingType();
        if (shippingType == ShippingCode.FREE.getValue()) {
            view.setupShippingFree();
            view.hideShippingCondition();
        } else if (shippingType == ShippingCode.PAID.getValue()) {
            view.setupShippingPrice(data.getShippingPrice());
            view.hideShippingCondition();
        } else if (shippingType == ShippingCode.CONDITIONAL.getValue()) {
            view.setupShippingPrice(data.getConditionShippingPrice());
            view.setupShippingCondition(data.getConditionFreeShipping());
            view.showShippingCondition();
        }

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

        view.setupReviewCount(StringUtil.toDigit(data.getReviewCount()));

        List<ReviewDataModel> reviews = data.getReviews();
        if (reviews != null) {
            reviewAdapter.set(data.getReviews());
            view.reviewRefresh();
            view.showReviewContentsPanel();
        }

        view.setupQuestionCount(StringUtil.toDigit(data.getQuestionCount()));

        List<ProductDataModel> relatedProducts = data.getRelatedProducts();
        if (relatedProducts != null) {
            relatedProductsAdapter.set(data.getRelatedProducts());
            view.relatedProductRefresh();
            view.showRelatedPanel();
        } else {
            view.hideRelatedPanel();
        }

        int displayCode = data.getDisplayCode();
        if (displayCode != DisplayCode.DISPLAY.getValue()) {
            view.showDisplayLabel(data.getDisplayLabel());
            view.disableBuyButton(data.getDisplayLabel());
            view.setupPriceOriginNoDisplayColor();
            view.setupDiscountPercentNoDisplayColor();
            view.setupPriceNoDisplayColor();
            if (displayCode == DisplayCode.STOP_SELLING.getValue()) view.hideScrap();
        }

        setupScrap();
    }

    private void setupScrap() {
        if (data.isScrap()) {
            view.checkScrap();
        } else {
            view.uncheckScrap();
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
        addDisposable(productRequest.getProductOptions(productNo)
            .compose(Transformer.applySchedulers())
            .filter(data -> data.getCode().equals(HttpCode.OK))
            .map((Function<BaseDataModel, List<OptionColorDataModel>>)
                data -> gson.fromJson(data.getData(), new TypeToken<ArrayList<OptionColorDataModel>>() {
                }.getType()))
            .subscribe(this::resProductOptions, Timber::e));
    }

    private void resProductOptions(List<OptionColorDataModel> options) {
        view.setupOptionSelector(data.getPrice(), options);
    }

    private void reqUpdateHelp(boolean isHelp, int reviewNo) {
        addDisposable(reviewRequest.updateHelp(isHelp, productNo, reviewNo)
            .subscribe());
    }

    private void syncReviewData(int reviewNo, boolean isHelp) {
        ReviewDataModel item = Observable.fromIterable(reviewAdapter.get())
            .filter(data -> data.getReviewNo() == reviewNo)
            .blockingFirst();
        if (item != null) {
            int helpCount = item.getHelpCount();
            item.setHelpCount(isHelp ? helpCount + 1 : helpCount - 1);
            item.setHelp(isHelp);
            view.reviewRefresh();
        }
    }

    private void toRxObservable() {
        addDisposable(RxBus.toObservable().subscribe(o -> {
            if (o instanceof RxEventProductDetailReviewHelpClick) {
                boolean isHelp = ((RxEventProductDetailReviewHelpClick) o).isHelp();
                int reviewNo = ((RxEventProductDetailReviewHelpClick) o).getReviewNo();
                reqUpdateHelp(isHelp, reviewNo);
            } else if (o instanceof ReviewPresenterImpl.RxEventReviewHelpClick) {
                int reviewNo = ((ReviewPresenterImpl.RxEventReviewHelpClick) o).getReviewNo();
                boolean isHelp = ((ReviewPresenterImpl.RxEventReviewHelpClick) o).isHelp();
                syncReviewData(reviewNo, isHelp);
            } else if (o instanceof QuestionWritePresenterImpl.RxEventQuestionWrite) {
                data.setQuestionCount(data.getQuestionCount() + 1);
                view.setupQuestionCount(StringUtil.toDigit(data.getQuestionCount()));
            }
        }, Timber::e));
    }

    @AllArgsConstructor @Getter public final static class RxEventProductDetailReviewHelpClick {
        boolean isHelp;
        int reviewNo;
    }
}