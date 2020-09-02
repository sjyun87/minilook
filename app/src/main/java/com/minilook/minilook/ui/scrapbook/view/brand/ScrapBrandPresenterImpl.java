package com.minilook.minilook.ui.scrapbook.view.brand;

import com.google.gson.Gson;
import com.minilook.minilook.data.model.brand.BrandDataModel;
import com.minilook.minilook.data.model.scrap.ScrapBrandDataModel;
import com.minilook.minilook.data.network.member.MemberRequest;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.scrapbook.view.brand.di.ScrapbookBrandArguments;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import timber.log.Timber;

public class ScrapBrandPresenterImpl extends BasePresenterImpl implements ScrapBrandPresenter {

    private final static int ROWS = 10;

    private final View view;
    private final BaseAdapterDataModel<BrandDataModel> adapter;
    private final MemberRequest memberRequest;

    private AtomicInteger page = new AtomicInteger(0);
    private Gson gson = new Gson();

    public ScrapBrandPresenterImpl(ScrapbookBrandArguments args) {
        view = args.getView();
        adapter = args.getAdapter();
        memberRequest = new MemberRequest();
    }

    @Override public void onCreate() {
        view.setupRecyclerView();

        reqScrapBrands();
    }

    private void reqScrapBrands() {
        addDisposable(memberRequest.getScrapBrands(page.incrementAndGet(), ROWS)
            .map(data -> gson.fromJson(data.getData(), ScrapBrandDataModel.class))
            .compose(Transformer.applySchedulers())
            .subscribe(this::resScrapBrands, Timber::e));
    }

    private void resScrapBrands(ScrapBrandDataModel data) {
        int start = adapter.getSize();
        int end = start + data.getBrands().size();

        adapter.addAll(parseToScrap(data.getBrands()));
        view.refresh(start, end);
    }

    private List<BrandDataModel> parseToScrap(List<BrandDataModel> brands) {
        for (BrandDataModel model : brands) {
            model.setScrap(true);
        }
        return brands;
    }
}