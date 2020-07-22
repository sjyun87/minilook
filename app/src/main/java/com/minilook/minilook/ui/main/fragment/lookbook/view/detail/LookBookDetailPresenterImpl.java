package com.minilook.minilook.ui.main.fragment.lookbook.view.detail;

import com.minilook.minilook.data.model.lookbook.LookBookDetailDataModel;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.main.fragment.lookbook.view.detail.di.LookBookDetailArguments;
import com.minilook.minilook.ui.main.fragment.lookbook.view.preview.LookBookPreviewPresenterImpl;
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
                LookBookDetailDataModel data = ((LookBookPreviewPresenterImpl.RxEventLookBookPageChange) o).getData();
                setupData(data);
            }
        }, Timber::e));
    }

    private void setupData(LookBookDetailDataModel data) {
        imageAdapter.set(data.getImage_urls());
        view.imageRefresh();

        view.setTitle(data.getTitle());
        view.setDescription(data.getDesc());
        productAdapter.set(data.getProducts());
        view.productRefresh();
    }
}