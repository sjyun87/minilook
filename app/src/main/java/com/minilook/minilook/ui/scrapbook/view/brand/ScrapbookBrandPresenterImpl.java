package com.minilook.minilook.ui.scrapbook.view.brand;

import com.google.gson.Gson;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.model.brand.BrandDataModel;
import com.minilook.minilook.data.model.scrap.ScrapBrandDataModel;
import com.minilook.minilook.data.network.scrap.ScrapRequest;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.base.widget.BottomBar;
import com.minilook.minilook.ui.main.MainPresenterImpl;
import com.minilook.minilook.ui.scrapbook.view.brand.di.ScrapbookBrandArguments;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import timber.log.Timber;

public class ScrapbookBrandPresenterImpl extends BasePresenterImpl implements ScrapbookBrandPresenter {

    private final static int ROWS = 10;

    private final View view;
    private final BaseAdapterDataModel<BrandDataModel> adapter;
    private final ScrapRequest scrapRequest;

    private AtomicInteger page = new AtomicInteger(0);
    private Gson gson = new Gson();

    public ScrapbookBrandPresenterImpl(ScrapbookBrandArguments args) {
        view = args.getView();
        adapter = args.getAdapter();
        scrapRequest = new ScrapRequest();
    }

    @Override public void onCreate() {
        view.setupRecyclerView();

        reqScrapBrands();
    }

    @Override public void onLoadMore() {
        reqLoadMoreScrapBrands();
    }

    @Override public void onEmptyClick() {
        RxBus.send(new MainPresenterImpl.RxEventNavigateToPage(BottomBar.POSITION_MARKET));
    }

    private void reqScrapBrands() {
        addDisposable(scrapRequest.getScrapBrands(page.incrementAndGet(), ROWS)
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                String code = data.getCode();
                if (HttpCode.NO_DATA.equals(code)) {
                    view.showEmptyPanel();
                }
                return code.equals(HttpCode.OK);
            })
            .map(data -> gson.fromJson(data.getData(), ScrapBrandDataModel.class))
            .subscribe(this::resScrapBrands, Timber::e));
    }

    private void resScrapBrands(ScrapBrandDataModel data) {
        if (data.getBrands().size() > 0) {
            adapter.set(parseToScrap(data.getBrands()));
            view.refresh();
        } else {
            view.showEmptyPanel();
        }
    }

    private void reqLoadMoreScrapBrands() {
        addDisposable(scrapRequest.getScrapBrands(page.incrementAndGet(), ROWS)
            .compose(Transformer.applySchedulers())
            .filter(data -> data.getCode().equals(HttpCode.OK))
            .map(data -> gson.fromJson(data.getData(), ScrapBrandDataModel.class))
            .subscribe(this::resLoadMoreScrapBrands, Timber::e));
    }

    private void resLoadMoreScrapBrands(ScrapBrandDataModel data) {
        int start = adapter.getSize();
        int rows = data.getBrands().size();

        adapter.addAll(parseToScrap(data.getBrands()));
        view.refresh(start, rows);
    }

    private List<BrandDataModel> parseToScrap(List<BrandDataModel> brands) {
        for (BrandDataModel model : brands) {
            model.setScrap(true);
        }
        return brands;
    }
}