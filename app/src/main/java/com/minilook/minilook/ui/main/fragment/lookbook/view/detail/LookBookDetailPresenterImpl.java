package com.minilook.minilook.ui.main.fragment.lookbook.view.detail;

import com.minilook.minilook.data.model.brand.BrandInfoDataModel;
import com.minilook.minilook.data.model.lookbook.LookBookDetailImageDataModel;
import com.minilook.minilook.data.model.lookbook.LookBookModuleDataModel;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.main.fragment.lookbook.view.detail.di.LookBookDetailArguments;
import com.minilook.minilook.ui.main.fragment.lookbook.view.preview.LookBookPreviewPresenterImpl;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Function;
import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class LookBookDetailPresenterImpl extends BasePresenterImpl implements LookBookDetailPresenter {

    private final View view;
    private final BaseAdapterDataModel<String> imageAdapter;
    private final BaseAdapterDataModel<ProductDataModel> productAdapter;

    public LookBookDetailPresenterImpl(LookBookDetailArguments args) {
        view = args.getView();
        imageAdapter = args.getImageAdapter();
        productAdapter = args.getProductAdapter();
    }

    @Override public void onCreate() {
        toRxObservable();

        view.setupViewPager();
        view.setupRecyclerView();
    }

    private void toRxObservable() {
        addDisposable(RxBus.toObservable().subscribe(o -> {
            if (o instanceof LookBookPreviewPresenterImpl.RxEventLookBookPageChange) {
                LookBookModuleDataModel data = ((LookBookPreviewPresenterImpl.RxEventLookBookPageChange) o).getData();
                setupData(data);
            }
        }, Timber::e));
    }

    private void setupData(LookBookModuleDataModel data) {
        imageAdapter.set(parseToList(data.getBg_detail_images()));
        view.imageRefresh();

        view.setTitle(data.getTitle());
        view.setDescription(data.getDesc());
        productAdapter.set(data.getProducts());
        view.productRefresh();
    }

    private List<String> parseToList(List<LookBookDetailImageDataModel> urls) {
        return Observable.fromIterable(urls)
            .map(LookBookDetailImageDataModel::getUrl)
            .toList()
            .blockingGet();
    }
}