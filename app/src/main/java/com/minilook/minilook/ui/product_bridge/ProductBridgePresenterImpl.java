package com.minilook.minilook.ui.product_bridge;

import com.google.gson.Gson;
import com.minilook.minilook.App;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.common.SortDataModel;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.data.model.search.SearchOptionDataModel;
import com.minilook.minilook.data.model.search.SearchResultDataModel;
import com.minilook.minilook.data.network.search.SearchRequest;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.product_bridge.di.ProductBridgeArguments;
import io.reactivex.rxjava3.functions.Function;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import timber.log.Timber;

public class ProductBridgePresenterImpl extends BasePresenterImpl implements ProductBridgePresenter {

    private static final int ROWS = 30;

    private final View view;
    private final SearchOptionDataModel options;
    private final BaseAdapterDataModel<ProductDataModel> productAdapter;
    private final SearchRequest searchRequest;
    private final List<SortDataModel> sortCodes;

    private Gson gson = new Gson();
    private AtomicInteger page = new AtomicInteger(0);

    private int totalPage;

    public ProductBridgePresenterImpl(ProductBridgeArguments args) {
        view = args.getView();
        options = args.getOptions();
        productAdapter = args.getProductAdapter();
        searchRequest = new SearchRequest();
        sortCodes = App.getInstance().getSortCodes();
    }

    @Override public void onCreate() {
        view.setupProductRecyclerView();

        reqProducts();
    }

    @Override public void onLoadMore() {
        if (totalPage > page.get()) {
            reqLoadMoreProducts();
        }
    }

    private void reqProducts() {
        options.setOrder(sortCodes.get(0).getCode());
        addDisposable(searchRequest.getProducts(page.incrementAndGet(), ROWS, options)
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                Timber.e(data.toString());
                String code = data.getCode();
                if (code.equals(HttpCode.NO_DATA)) {
                    Timber.e("NO DATA");
                    // TODO empty 처리
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