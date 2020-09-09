package com.minilook.minilook.ui.product_bridge;

import com.google.gson.Gson;
import com.minilook.minilook.App;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.common.CategoryDataModel;
import com.minilook.minilook.data.model.common.SortDataModel;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.data.model.search.FilterDataModel;
import com.minilook.minilook.data.model.search.SearchOptionDataModel;
import com.minilook.minilook.data.model.search.SearchResultDataModel;
import com.minilook.minilook.data.network.search.SearchRequest;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.product_bridge.di.ProductBridgeArguments;
import io.reactivex.rxjava3.functions.Function;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import timber.log.Timber;

public class ProductBridgePresenterImpl extends BasePresenterImpl implements ProductBridgePresenter {

    private static final int ROWS = 30;

    private final View view;
    private SearchOptionDataModel options;
    private final BaseAdapterDataModel<SortDataModel> sortAdapter;
    private final BaseAdapterDataModel<ProductDataModel> productAdapter;
    private final SearchRequest searchRequest;
    private final List<SortDataModel> sortCodes;

    private Gson gson = new Gson();
    private AtomicInteger page = new AtomicInteger(0);

    private int totalPage;

    private boolean isVisibleCategoryDepth1;
    private List<CategoryDataModel> categoryOptions = new ArrayList<>();
    private String selectedSortCode;
    private boolean isSortVisible = false;

    public ProductBridgePresenterImpl(ProductBridgeArguments args) {
        view = args.getView();
        options = args.getOptions();
        sortAdapter = args.getSortAdapter();
        productAdapter = args.getProductAdapter();
        searchRequest = new SearchRequest();
        sortCodes = App.getInstance().getSortCodes();
    }

    @Override public void onCreate() {
        view.setupSortRecyclerView();
        view.setupProductRecyclerView();

        initData();
    }

    @Override public void onLoadMore() {
        if (totalPage > page.get()) {
            reqLoadMoreProducts();
        }
    }

    @Override public void onTabClick(int position) {
        String categoryCode = categoryOptions.get(position).getCode();
        if (isVisibleCategoryDepth1) {
            options.setCategory_code(categoryCode);
        } else {
            options.setCategory_derail_code(categoryCode);
        }
        reqProducts();
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
        if (!selectedSortCode.equals(data.getCode())) {
            selectedSortCode = data.getCode();
            options.setOrder(selectedSortCode);
            view.setupSortText(data.getName());

            productAdapter.clear();
            page = new AtomicInteger(0);
            reqProducts();
        }
        view.hideSortPanel();
        isSortVisible = false;
    }

    @Override public void onFilterClick() {
        view.navigateToSearchFilter();
        view.finish();
    }

    private void initData() {
        isVisibleCategoryDepth1 = options.getCategory_code() == null;
        if (!isVisibleCategoryDepth1) view.setupTitle(options.getCategory_name());

        String categoryCode = isVisibleCategoryDepth1 ? "" : options.getCategory_code();
        reqFilters(categoryCode);

        options.setOrder(sortCodes.get(0).getCode());
        setupSortData();
    }

    private void setupSortData() {
        List<SortDataModel> sortItems = App.getInstance().getSortCodes();
        sortAdapter.set(sortItems);
        view.sortRefresh();
        view.setupSortText(sortItems.get(0).getName());
        selectedSortCode = sortItems.get(0).getCode();

        options.setOrder(sortItems.get(0).getCode());
        reqProducts();
    }

    private void reqFilters(String category_code) {
        addDisposable(searchRequest.getFilterOptions(category_code)
            .compose(Transformer.applySchedulers())
            .filter(data -> data.getCode().equals(HttpCode.OK))
            .map(data -> gson.fromJson(data.getData(), FilterDataModel.class))
            .subscribe(this::resFilterOptions, Timber::e));
    }

    private void resFilterOptions(FilterDataModel data) {
        view.setupTabItems(getInitCategoryData(data.getCategories()));
    }

    private List<CategoryDataModel> getInitCategoryData(List<CategoryDataModel> categories) {
        for (int i = 0; i < categories.size() + 1; i++) {
            CategoryDataModel model;
            if (i == 0) {
                model = new CategoryDataModel();
                model.setName("전체");
                model.setCode("");
                model.setSelected(true);
            } else {
                model = categories.get(i - 1);
                model.setSelected(false);
            }
            model.setPosition(i);
            categoryOptions.add(model);
        }
        return categoryOptions;
    }

    private void reqProducts() {
        page = new AtomicInteger(0);
        addDisposable(searchRequest.getProducts(page.incrementAndGet(), ROWS, options)
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                String code = data.getCode();
                if (code.equals(HttpCode.NO_DATA)) {
                    view.showEmptyPanel();
                }
                return code.equals(HttpCode.OK);
            })
            .map((Function<BaseDataModel, SearchResultDataModel>)
                data -> gson.fromJson(data.getData(), SearchResultDataModel.class))
            .subscribe(this::resProducts, Timber::e));
    }

    private void resProducts(SearchResultDataModel data) {
        totalPage = data.getTotal();

        productAdapter.set(data.getProducts());
        view.productRefresh();
        view.hideEmptyPanel();
    }

    private void reqLoadMoreProducts() {
        addDisposable(searchRequest.getProducts(page.incrementAndGet(), ROWS, options)
            .compose(Transformer.applySchedulers())
            .filter(data -> data.getCode().equals(HttpCode.OK))
            .map((Function<BaseDataModel, SearchResultDataModel>)
                data -> gson.fromJson(data.getData(), SearchResultDataModel.class))
            .subscribe(this::resLoadMoreProducts, Timber::e));
    }

    private void resLoadMoreProducts(SearchResultDataModel data) {
        int start = productAdapter.getSize();
        int rows = data.getProducts().size();
        productAdapter.addAll(data.getProducts());
        view.productRefresh(start, rows);
    }
}