package com.minilook.minilook.ui.scrapbook.view.product;

import com.google.gson.Gson;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.data.model.scrap.ScrapProductDataModel;
import com.minilook.minilook.data.network.scrap.ScrapRequest;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.base.widget.BottomBar;
import com.minilook.minilook.ui.main.MainPresenterImpl;
import com.minilook.minilook.ui.scrapbook.view.product.di.ScrapbookProductArguments;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import timber.log.Timber;

public class ScrapbookProductPresenterImpl extends BasePresenterImpl implements ScrapbookProductPresenter {

    private final static int ROWS = 30;

    private final View view;
    private final BaseAdapterDataModel<ProductDataModel> adapter;
    private final ScrapRequest scrapRequest;

    private AtomicInteger page = new AtomicInteger(0);
    private Gson gson = new Gson();

    public ScrapbookProductPresenterImpl(ScrapbookProductArguments args) {
        view = args.getView();
        adapter = args.getAdapter();
        scrapRequest = new ScrapRequest();
    }

    @Override public void onCreate() {
        view.setupRecyclerView();

        reqScrapProduct();
    }

    @Override public void onLoadMore() {
        reqLoadMoreScrapProduct();
    }

    @Override public void onEmptyClick() {
        RxBus.send(new MainPresenterImpl.RxEventNavigateToPage(BottomBar.POSITION_MARKET));
    }

    @Override public void onProductScrap(boolean isScrap, ProductDataModel product) {
        int position = adapter.get(product);
        adapter.remove(product);
        view.refresh(position);
        if (adapter.getSize() == 0) view.showEmptyPanel();
    }

    private void reqScrapProduct() {
        addDisposable(scrapRequest.getScrapProducts(page.incrementAndGet(), ROWS)
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                String code = data.getCode();
                if (HttpCode.NO_DATA.equals(code)) {
                    view.showEmptyPanel();
                }
                return code.equals(HttpCode.OK);
            })
            .map(data -> gson.fromJson(data.getData(), ScrapProductDataModel.class))
            .subscribe(this::resScrapProducts, Timber::e));
    }

    private void resScrapProducts(ScrapProductDataModel data) {
        adapter.set(parseToScrap(data.getProducts()));
        view.refresh();
    }

    private void reqLoadMoreScrapProduct() {
        addDisposable(scrapRequest.getScrapProducts(page.incrementAndGet(), ROWS)
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                String code = data.getCode();
                return code.equals(HttpCode.OK);
            })
            .map(data -> gson.fromJson(data.getData(), ScrapProductDataModel.class))
            .subscribe(this::resLoadMoreScrapProducts, Timber::e));
    }

    private void resLoadMoreScrapProducts(ScrapProductDataModel data) {
        int start = adapter.getSize();
        int rows = data.getProducts().size();

        adapter.addAll(parseToScrap(data.getProducts()));
        view.refresh(start, rows);
    }

    private List<ProductDataModel> parseToScrap(List<ProductDataModel> products) {
        for (ProductDataModel model : products) {
            model.setScrap(true);
        }
        return products;
    }
}