package com.minilook.minilook.ui.brand_detail;

import com.google.gson.Gson;
import com.minilook.minilook.App;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.model.brand.BrandDataModel;
import com.minilook.minilook.data.model.common.SortDataModel;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.data.model.search.SearchDataModel;
import com.minilook.minilook.data.model.search.SearchOptionDataModel;
import com.minilook.minilook.data.network.brand.BrandRequest;
import com.minilook.minilook.data.network.search.SearchRequest;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.brand_detail.di.BrandDetailArguments;
import com.minilook.minilook.util.StringUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import timber.log.Timber;

public class BrandDetailPresenterImpl extends BasePresenterImpl implements BrandDetailPresenter {

    private final View view;
    private final int brand_id;
    private final BaseAdapterDataModel<String> styleAdapter;
    private final BaseAdapterDataModel<SortDataModel> sortAdapter;
    private final BaseAdapterDataModel<ProductDataModel> productAdapter;
    private final BrandRequest brandRequest;
    private final SearchRequest searchRequest;

    private static final int ROWS = 30;

    private AtomicInteger page = new AtomicInteger(0);
    private Gson gson = new Gson();
    private boolean isSortVisible = false;
    private String sortCode;
    private int totalPageSize;

    public BrandDetailPresenterImpl(BrandDetailArguments args) {
        view = args.getView();
        brand_id = args.getBrand_id();
        styleAdapter = args.getStyleAdapter();
        sortAdapter = args.getSortAdapter();
        productAdapter = args.getProductAdapter();
        brandRequest = new BrandRequest();
        searchRequest = new SearchRequest();
    }

    @Override public void onCreate() {
        view.setupScrollView();
        view.setupStyleRecyclerView();
        view.setupSortRecyclerView();
        view.setupProductRecyclerView();
        reqBrandDetail();
        setupSortData();
    }

    @Override public void onSortClick() {
        if (isSortVisible) {
            view.hideSortPanel();
        } else {
            view.showSortPanel();
        }
        isSortVisible = !isSortVisible;
    }

    @Override public void onSortSelected(SortDataModel data) {
        if (!sortCode.equals(data.getCode())) {
            sortCode = data.getCode();
            view.setupSortText(data.getName());

            productAdapter.clear();
            page = new AtomicInteger(0);
            reqProducts();
        }
        view.hideSortPanel();
        isSortVisible = false;
    }

    @Override public void onLoadMore() {
        reqProducts();
    }

    @Override public void onBrandInfoClick() {
        view.navigateToBrandInfo(brand_id);
    }

    private void reqBrandDetail() {
        addDisposable(
            brandRequest.getBrandDetail(brand_id)
                .compose(Transformer.applySchedulers())
                .filter(data -> data.getCode().equals(HttpCode.OK))
                .map(data -> gson.fromJson(data.getData(), BrandDataModel.class))
                .subscribe(this::resBrandDetail, Timber::e)
        );
    }

    private void resBrandDetail(BrandDataModel data) {
        view.setupThumb(data.getImage_url());
        view.setupLogo(data.getBrand_logo());
        view.setupScrapCount(StringUtil.toDigit(data.getScrap_cnt()));
        view.setupName(data.getBrand_name());
        if (data.getBrand_tag() != null && !data.getBrand_tag().equals("")) view.setupTag(data.getBrand_tag().replace(",", " "));
        view.setupDesc(data.getBrand_desc());
        styleAdapter.set(checkValid(data.getStyle_images()));
        view.styleRefresh();
    }

    private List<String> checkValid(List<String> images) {
        List<String> items = new ArrayList<>();
        for (String url : images) {
            if (url != null && !url.equals("")) items.add(url);
        }
        return items;
    }

    private void setupSortData() {
        List<SortDataModel> sortItems = App.getInstance().getSortCodes();
        sortAdapter.set(sortItems);
        view.sortRefresh();
        sortCode = sortItems.get(0).getCode();
        view.setupSortText(sortItems.get(0).getName());

        reqProducts();
    }

    private void reqProducts() {
        if (page.get() != 0 && page.get() >= totalPageSize) return;
        addDisposable(
            searchRequest.getProducts(parseToModel())
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
        options.setBrand_id(brand_id);
        options.setPage(page.incrementAndGet());
        options.setRow(ROWS);
        options.setOrder(sortCode);
        return options;
    }
}