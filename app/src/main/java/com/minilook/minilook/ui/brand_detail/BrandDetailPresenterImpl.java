package com.minilook.minilook.ui.brand_detail;

import com.google.gson.Gson;
import com.minilook.minilook.App;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.model.brand.BrandDataModel;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.data.model.search.SearchDataModel;
import com.minilook.minilook.data.model.search.SearchOptionDataModel;
import com.minilook.minilook.data.network.brand.BrandRequest;
import com.minilook.minilook.data.network.search.SearchRequest;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.brand_detail.di.BrandDetailArguments;
import com.minilook.minilook.ui.main.MainPresenterImpl;
import com.minilook.minilook.util.DynamicLinkUtil;
import com.minilook.minilook.util.TrackingUtil;
import java.util.concurrent.atomic.AtomicInteger;
import timber.log.Timber;

public class BrandDetailPresenterImpl extends BasePresenterImpl implements BrandDetailPresenter {

    private static final int ROWS = 30;

    private final View view;
    private final int brandNo;
    private final BaseAdapterDataModel<String> styleAdapter;
    private final BaseAdapterDataModel<ProductDataModel> productAdapter;
    private final BrandRequest brandRequest;
    private final SearchRequest searchRequest;
    private final Gson gson;

    private AtomicInteger page = new AtomicInteger(0);

    private BrandDataModel data;

    private boolean isSortPanelVisible = false;
    private String sortCode;
    private int totalPageSize;

    public BrandDetailPresenterImpl(BrandDetailArguments args) {
        view = args.getView();
        brandNo = args.getBrandNo();
        styleAdapter = args.getStyleAdapter();
        productAdapter = args.getProductAdapter();
        brandRequest = new BrandRequest();
        searchRequest = new SearchRequest();
        gson = App.getInstance().getGson();
    }

    @Override public void onCreate() {
        view.setupClickAction();
        view.setupStyleRecyclerView();
        view.setupProductRecyclerView();

        getBrandDetail();
        setupSortData();
    }

    @Override public void onResume() {
        TrackingUtil.pageTracking("브랜드 상세페이지", BrandDetailActivity.class.getSimpleName());
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
            RxBus.send(new MainPresenterImpl.RxBusEventUpdateBrandScrap(data));
        } else {
            view.navigateToLogin();
        }
    }

    @Override public void onSortClick() {
        isSortPanelVisible = !isSortPanelVisible;
        if (isSortPanelVisible) {
            view.showSortPanel();
        } else {
            view.hideSortPanel();
        }
    }

    //@Override public void onSortSelected(CodeDataModel data) {
    //    if (!sortCode.equals(data.getCode())) {
    //        sortCode = data.getCode();
    //        view.setupSortText(data.getName());
    //
    //        productAdapter.clear();
    //        page = new AtomicInteger(0);
    //        reqProducts();
    //    }
    //    view.hideSortPanel();
    //    isSortPanelVisible = false;
    //}

    @Override public void onLoadMore() {
        getProducts();
    }

    @Override public void onBrandInfoClick() {
        view.navigateToBrandInfo(brandNo);
    }

    @Override public void onShareClick() {
        DynamicLinkUtil.sendDynamicLink(DynamicLinkUtil.TYPE_BRAND, brandNo, data.getBrandName(), data.getImageUrl());
    }

    private void getBrandDetail() {
        addDisposable(
            brandRequest.getBrandDetail(brandNo)
                .compose(Transformer.applySchedulers())
                .filter(data -> {
                    String code = data.getCode();
                    if (!code.equals(HttpCode.OK)) {
                        view.showErrorDialog();
                    }
                    return code.equals(HttpCode.OK);
                })
                .map(data -> gson.fromJson(data.getData(), BrandDataModel.class))
                .subscribe(this::onResBrandDetail, Timber::e)
        );
    }

    private void onResBrandDetail(BrandDataModel data) {
        this.data = data;

        view.setupThumb(data.getImageUrl());
        view.setupLogo(data.getBrandLogo());
        setupScrap();
        view.setupName(data.getBrandName());
        view.setupTag(data.getBrandTag());
        view.setupDesc(data.getBrandDesc());

        styleAdapter.set(data.getStyleImages());
        view.styleRefresh();
    }

    private void setupScrap() {
        if (data.isScrap()) {
            view.scrapOn();
        } else {
            view.scrapOff();
        }
        view.setupScrapCount(data.getScrapCount());
    }

    private void setupSortData() {
        //sortAdapter.set(App.getInstance().getSortCodes());
        //view.sortRefresh();
        sortCode = App.getInstance().getSortCodes().get(0).getCode();
        view.setupSortText(App.getInstance().getSortCodes().get(0).getName());

        getProducts();
    }

    private void getProducts() {
        if (page.get() != 0 && page.get() >= totalPageSize) return;
        addDisposable(
            searchRequest.getProducts(page.incrementAndGet(), ROWS, parseToModel())
                .compose(Transformer.applySchedulers())
                .filter(data -> data.getCode().equals(HttpCode.OK))
                .map(data -> gson.fromJson(data.getData(), SearchDataModel.class))
                .subscribe(this::resProducts, Timber::e)
        );
    }

    private SearchOptionDataModel parseToModel() {
        SearchOptionDataModel options = new SearchOptionDataModel();
        options.setBrandNo(brandNo);
        options.setSortCode(sortCode);
        return options;
    }

    private void resProducts(SearchDataModel data) {
        totalPageSize = data.getTotal();
        int start = productAdapter.getSize();
        int row = data.getProducts().size();
        productAdapter.addAll(data.getProducts());
        view.productRefresh(start, row);

        view.scrollToTop();
    }
}