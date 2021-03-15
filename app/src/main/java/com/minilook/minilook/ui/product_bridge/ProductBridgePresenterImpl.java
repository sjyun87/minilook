package com.minilook.minilook.ui.product_bridge;

import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.minilook.minilook.App;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.common.CodeDataModel;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.data.model.search.FilterDataModel;
import com.minilook.minilook.data.model.search.SearchOptionDataModel;
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
    private final SearchOptionDataModel options;
    private final BaseAdapterDataModel<CodeDataModel> sortAdapter;
    private final BaseAdapterDataModel<ProductDataModel> productAdapter;
    private final SearchRequest searchRequest;
    private final List<CodeDataModel> sortCodes;

    private Gson gson = new Gson();
    private AtomicInteger page = new AtomicInteger(0);

    private int totalPageSize;

    private boolean isVisibleCategoryDepth1;
    private List<CodeDataModel> categoryOptions = new ArrayList<>();
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
        if (totalPageSize > page.get()) {
            reqLoadMoreProducts();
        }
    }

    @Override public void onTabClick(int position) {
        String categoryCode = categoryOptions.get(position).getCode();
        if (isVisibleCategoryDepth1) {
            options.setCategoryCode(categoryCode);
        } else {
            options.setCategoryDerailCode(categoryCode);
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

    @Override public void onSortSelected(CodeDataModel data) {
        if (!selectedSortCode.equals(data.getCode())) {
            selectedSortCode = data.getCode();
            options.setSortCode(selectedSortCode);
            view.setupSortText(data.getName());

            productAdapter.clear();
            page = new AtomicInteger(0);
            reqProducts();
        }
        view.hideSortPanel();
        isSortVisible = false;
    }

    @Override public void onFilterClick() {
        view.navigateToSearchFilter(options);
        view.finish();
    }

    private void initData() {
        String keyword = options.getKeyword();
        if (!TextUtils.isEmpty(keyword)) {
            view.setSearchKeyword(keyword);
        }

        isVisibleCategoryDepth1 = options.getCategoryCode() == null;
        if (!isVisibleCategoryDepth1) view.setupTitle(options.getCategoryName());

        String categoryCode = isVisibleCategoryDepth1 ? "" : options.getCategoryCode();
        reqFilters(categoryCode);

        options.setSortCode(sortCodes.get(0).getCode());
        setupSortData();
    }

    private void setupSortData() {
        List<CodeDataModel> sortItems = App.getInstance().getSortCodes();
        sortAdapter.set(sortItems);
        view.sortRefresh();
        view.setupSortText(sortItems.get(0).getName());
        selectedSortCode = sortItems.get(0).getCode();

        options.setSortCode(sortItems.get(0).getCode());
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

    private List<CodeDataModel> getInitCategoryData(List<CodeDataModel> categories) {
        for (int i = 0; i < categories.size() + 1; i++) {
            CodeDataModel model;
            if (i == 0) {
                model = new CodeDataModel();
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
            .map((Function<BaseDataModel, List<ProductDataModel>>)
                data -> {
                    totalPageSize = data.getTotalPage();
                    return gson.fromJson(data.getData(), new TypeToken<ArrayList<ProductDataModel>>() {
                    }.getType());
                })
            .subscribe(this::resProducts, Timber::e));
    }

    private void resProducts(List<ProductDataModel> data) {
        productAdapter.set(data);
        view.productRefresh();
        view.hideEmptyPanel();
    }

    private void reqLoadMoreProducts() {
        addDisposable(searchRequest.getProducts(page.incrementAndGet(), ROWS, options)
            .compose(Transformer.applySchedulers())
            .filter(data -> data.getCode().equals(HttpCode.OK))
            .map((Function<BaseDataModel, List<ProductDataModel>>)
                data -> gson.fromJson(data.getData(), new TypeToken<ArrayList<ProductDataModel>>() {
                }.getType()))
            .subscribe(this::resLoadMoreProducts, Timber::e));
    }

    private void resLoadMoreProducts(List<ProductDataModel> data) {
        int start = productAdapter.getSize();
        int rows = data.size();
        productAdapter.addAll(data);
        view.productRefresh(start, rows);
    }
}