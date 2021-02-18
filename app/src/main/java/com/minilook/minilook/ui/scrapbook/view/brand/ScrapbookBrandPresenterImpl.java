package com.minilook.minilook.ui.scrapbook.view.brand;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.minilook.minilook.App;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.brand.BrandDataModel;
import com.minilook.minilook.data.network.scrap.ScrapRequest;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.base.widget.BottomBar;
import com.minilook.minilook.ui.main.MainPresenterImpl;
import com.minilook.minilook.ui.scrapbook.view.brand.di.ScrapbookBrandArguments;
import io.reactivex.rxjava3.functions.Function;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import timber.log.Timber;

public class ScrapbookBrandPresenterImpl extends BasePresenterImpl implements ScrapbookBrandPresenter {

    private static final int ROWS = 10;

    private final View view;
    private final BaseAdapterDataModel<BrandDataModel> adapter;
    private final ScrapRequest scrapRequest;
    private final Gson gson;

    private AtomicInteger page;

    private int totalPageSize;

    public ScrapbookBrandPresenterImpl(ScrapbookBrandArguments args) {
        view = args.getView();
        adapter = args.getAdapter();
        scrapRequest = new ScrapRequest();
        gson = App.getInstance().getGson();
    }

    @Override public void onCreateView() {
        view.setupClickAction();
        view.setupRecyclerView();

        getScrapBrands();
    }

    @Override public void onDestroyView() {
        view.clear();
    }

    @Override public void onLoadMore() {
        if (totalPageSize > page.get()) getMoreScrapBrands();
    }

    @Override public void onEmptyClick() {
        RxBus.send(new MainPresenterImpl.RxEventNavigateToPage(BottomBar.POSITION_MARKET));
    }

    @Override public void onBrandScrap(BrandDataModel $data) {
        replaceBrandScrapData($data);
    }

    private void getScrapBrands() {
        page = new AtomicInteger(0);
        addDisposable(scrapRequest.getScrapBrands(page.incrementAndGet(), ROWS)
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
            .map((Function<BaseDataModel, List<BrandDataModel>>) data -> {
                totalPageSize = data.getTotalPage();
                return gson.fromJson(data.getData(), new TypeToken<ArrayList<BrandDataModel>>() {
                }.getType());
            })
            .subscribe(this::onResScrapBrands, Timber::e));
    }

    private void onResScrapBrands(List<BrandDataModel> data) {
        adapter.set(data);
        view.refresh();
    }

    private void getMoreScrapBrands() {
        addDisposable(scrapRequest.getScrapBrands(page.incrementAndGet(), ROWS)
            .compose(Transformer.applySchedulers())
            .filter(data -> data.getCode().equals(HttpCode.OK))
            .map((Function<BaseDataModel, List<BrandDataModel>>)
                data -> gson.fromJson(data.getData(), new TypeToken<ArrayList<BrandDataModel>>() {
                }.getType()))
            .subscribe(this::onResMoreScrapBrands, Timber::e));
    }

    private void onResMoreScrapBrands(List<BrandDataModel> data) {
        int start = adapter.getSize();
        int rows = data.size();

        adapter.addAll(data);
        view.refresh(start, rows);
    }

    private void replaceBrandScrapData(BrandDataModel $data) {
        BrandDataModel targetData = null;
        for (BrandDataModel brand : adapter.get()) {
            if (brand.getBrandNo() == $data.getBrandNo()) {
                targetData = brand;
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
}