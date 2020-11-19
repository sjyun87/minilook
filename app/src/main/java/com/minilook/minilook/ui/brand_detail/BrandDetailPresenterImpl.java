package com.minilook.minilook.ui.brand_detail;

import android.net.Uri;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.minilook.minilook.App;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.model.brand.BrandDataModel;
import com.minilook.minilook.data.model.common.CodeDataModel;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.data.model.search.SearchDataModel;
import com.minilook.minilook.data.model.search.SearchOptionDataModel;
import com.minilook.minilook.data.network.brand.BrandRequest;
import com.minilook.minilook.data.network.scrap.ScrapRequest;
import com.minilook.minilook.data.network.search.SearchRequest;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.brand_detail.di.BrandDetailArguments;
import com.minilook.minilook.util.DynamicLinkManager;
import com.minilook.minilook.util.TrackingManager;
import java.util.concurrent.atomic.AtomicInteger;
import timber.log.Timber;

public class BrandDetailPresenterImpl extends BasePresenterImpl implements BrandDetailPresenter {

    private final View view;
    private final int brandNo;
    private final BaseAdapterDataModel<String> styleAdapter;
    private final BaseAdapterDataModel<CodeDataModel> sortAdapter;
    private final BaseAdapterDataModel<ProductDataModel> productAdapter;
    private DynamicLinkManager dynamicLinkManager;
    private final BrandRequest brandRequest;
    private final SearchRequest searchRequest;
    private final ScrapRequest scrapRequest;

    private static final int ROWS = 30;

    private AtomicInteger page = new AtomicInteger(0);
    private Gson gson = new Gson();
    private BrandDataModel data;
    private boolean isScrap;
    private int scrapCount = 0;
    private boolean isSortPanelVisible = false;
    private String sortCode;
    private int totalPageSize;

    public BrandDetailPresenterImpl(BrandDetailArguments args) {
        view = args.getView();
        brandNo = args.getBrandNo();
        styleAdapter = args.getStyleAdapter();
        sortAdapter = args.getSortAdapter();
        productAdapter = args.getProductAdapter();
        dynamicLinkManager = args.getDynamicLinkManager();
        brandRequest = new BrandRequest();
        searchRequest = new SearchRequest();
        scrapRequest = new ScrapRequest();
    }

    @Override public void onCreate() {
        view.setupScrollView();
        view.setupStyleRecyclerView();
        view.setupSortRecyclerView();
        view.setupProductRecyclerView();

        setupSortData();
        reqBrandDetail();
    }

    @Override public void onResume() {
        TrackingManager.pageTracking("브랜드 상세페이지", BrandDetailActivity.class.getSimpleName());
    }

    @Override public void onScrapClick() {
        if (!App.getInstance().isLogin()) {
            view.navigateToLogin();
            return;
        }

        isScrap = !isScrap;
        scrapCount = isScrap ? ++scrapCount : --scrapCount;
        setupScrap();
        //RxBus.send(new RxBusEvent.RxBusEventBrandScrap(isScrap, ));
        reqBrandScrap(isScrap);
    }

    @Override public void onSortClick() {
        isSortPanelVisible = !isSortPanelVisible;
        if (isSortPanelVisible) {
            view.showSortPanel();
        } else {
            view.hideSortPanel();
        }
    }

    @Override public void onSortSelected(CodeDataModel data) {
        if (!sortCode.equals(data.getCode())) {
            sortCode = data.getCode();
            view.setupSortText(data.getName());

            productAdapter.clear();
            page = new AtomicInteger(0);
            reqProducts();
        }
        view.hideSortPanel();
        isSortPanelVisible = false;
    }

    @Override public void onLoadMore() {
        reqProducts();
    }

    @Override public void onBrandInfoClick() {
        view.navigateToBrandInfo(brandNo);
    }

    @Override public void onShareClick() {
        dynamicLinkManager.createShareLink(DynamicLinkManager.TYPE_BRAND, brandNo, data.getBrandName(),
            data.getImageUrl(),
            new DynamicLinkManager.OnCompletedListener() {
                @Override public void onSuccess(Uri uri) {
                    view.sendLink(uri.toString());
                }

                @Override public void onFail() {
                    view.showErrorMessage();
                }
            });
    }

    private void reqBrandDetail() {
        addDisposable(
            brandRequest.getBrandDetail(brandNo)
                .compose(Transformer.applySchedulers())
                .filter(data -> data.getCode().equals(HttpCode.OK))
                .map(data -> gson.fromJson(data.getData(), BrandDataModel.class))
                .subscribe(this::resBrandDetail, Timber::e)
        );
    }

    private void resBrandDetail(BrandDataModel data) {
        this.data = data;

        view.setupThumb(data.getImageUrl());
        view.setupLogo(data.getBrandLogo());
        isScrap = data.isScrap();
        setupScrap();
        scrapCount = data.getScrapCount();
        view.setupScrapCount(scrapCount);
        view.setupName(data.getBrandName());
        if (!TextUtils.isEmpty(data.getBrandTag())) view.setupTag(data.getBrandTag());
        view.setupDesc(data.getBrandDesc());
        styleAdapter.set(data.getStyleImages());
        view.styleRefresh();
    }

    private void setupScrap() {
        if (isScrap) {
            view.scrapOn();
        } else {
            view.scrapOff();
        }
        view.setupScrapCount(scrapCount);
    }

    private void reqBrandScrap(boolean isScrap) {
        addDisposable(scrapRequest.updateBrandScrap(isScrap, brandNo)
            .subscribe());
    }

    private void setupSortData() {
        sortAdapter.set(App.getInstance().getSortCodes());
        view.sortRefresh();
        sortCode = sortAdapter.get(0).getCode();
        view.setupSortText(sortAdapter.get(0).getName());

        reqProducts();
    }

    private void reqProducts() {
        if (page.get() != 0 && page.get() >= totalPageSize) return;
        addDisposable(
            searchRequest.getProducts(page.incrementAndGet(), ROWS, parseToModel())
                .compose(Transformer.applySchedulers())
                .filter(data -> data.getCode().equals(HttpCode.OK))
                .map(data -> gson.fromJson(data.getData(), SearchDataModel.class))
                .subscribe(this::resProducts, Timber::e)
        );
    }

    private void resProducts(SearchDataModel data) {
        totalPageSize = data.getTotal();
        int start = productAdapter.getSize();
        int row = data.getProducts().size();
        productAdapter.addAll(data.getProducts());
        view.productRefresh(start, row);

        view.scrollToTop();
    }

    private SearchOptionDataModel parseToModel() {
        SearchOptionDataModel options = new SearchOptionDataModel();
        options.setBrand_no(brandNo);
        options.setSort_code(sortCode);
        return options;
    }
}