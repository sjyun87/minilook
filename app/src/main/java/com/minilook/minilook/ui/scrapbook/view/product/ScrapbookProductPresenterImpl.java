package com.minilook.minilook.ui.scrapbook.view.product;

import com.google.gson.Gson;
import com.minilook.minilook.App;
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
import java.util.concurrent.atomic.AtomicInteger;
import timber.log.Timber;

public class ScrapbookProductPresenterImpl extends BasePresenterImpl implements ScrapbookProductPresenter {

    private final static int ROWS = 30;

    private final View view;
    private final BaseAdapterDataModel<ProductDataModel> adapter;
    private final ScrapRequest scrapRequest;
    private final Gson gson;

    private AtomicInteger page;

    public ScrapbookProductPresenterImpl(ScrapbookProductArguments args) {
        view = args.getView();
        adapter = args.getAdapter();
        scrapRequest = new ScrapRequest();
        gson = App.getInstance().getGson();
    }

    @Override public void onCreateView() {
        view.setupClickAction();
        view.setupRecyclerView();

        getScrapProduct();
    }

    @Override public void onDestroyView() {
        view.clear();
    }

    @Override public void onLoadMore() {
        getMoreScrapProduct();
    }

    @Override public void onEmptyClick() {
        RxBus.send(new MainPresenterImpl.RxEventNavigateToPage(BottomBar.POSITION_MARKET));
    }

    @Override public void onProductScrap(ProductDataModel $data) {
        replaceProductScrapData($data);
    }

    private void replaceProductScrapData(ProductDataModel $data) {
        ProductDataModel targetData = null;
        for (ProductDataModel product : adapter.get()) {
            if (product.getProductNo() == $data.getProductNo()) {
                targetData = product;
                break;
            }
        }

        if (targetData != null) {
            int position = adapter.get(targetData);
            adapter.remove(targetData);
            view.refresh(position);
            if (adapter.getSize() == 0) view.showEmptyPanel();
        }
    }

    private void getScrapProduct() {
        page = new AtomicInteger(0);
        addDisposable(scrapRequest.getScrapProducts(page.incrementAndGet(), ROWS)
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                String code = data.getCode();
                if (code.equals(HttpCode.NO_DATA)) {
                    view.showEmptyPanel();
                } else if (!code.equals(HttpCode.OK)) {
                    view.showErrorDialog();
                }
                return code.equals(HttpCode.OK);
            })
            .map(data -> gson.fromJson(data.getData(), ScrapProductDataModel.class))
            .subscribe(this::onResScrapProducts, Timber::e));
    }

    private void onResScrapProducts(ScrapProductDataModel data) {
        adapter.set(data.getProducts());
        view.refresh();
    }

    private void getMoreScrapProduct() {
        addDisposable(scrapRequest.getScrapProducts(page.incrementAndGet(), ROWS)
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                String code = data.getCode();
                return code.equals(HttpCode.OK);
            })
            .map(data -> gson.fromJson(data.getData(), ScrapProductDataModel.class))
            .subscribe(this::onResMoreScrapProducts, Timber::e));
    }

    private void onResMoreScrapProducts(ScrapProductDataModel data) {
        int start = adapter.getSize();
        int rows = data.getProducts().size();

        adapter.addAll(data.getProducts());
        view.refresh(start, rows);
    }
}