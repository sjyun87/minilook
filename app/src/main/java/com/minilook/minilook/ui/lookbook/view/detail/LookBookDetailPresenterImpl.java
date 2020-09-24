package com.minilook.minilook.ui.lookbook.view.detail;

import com.minilook.minilook.data.model.lookbook.LookBookModuleDataModel;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.lookbook.LookBookPresenterImpl;
import com.minilook.minilook.ui.lookbook.view.detail.di.LookBookDetailArguments;
import com.minilook.minilook.ui.lookbook.view.preview.LookBookPreviewPresenterImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import timber.log.Timber;

public class LookBookDetailPresenterImpl extends BasePresenterImpl implements LookBookDetailPresenter {

    private final View view;
    private final BaseAdapterDataModel<String> styleAdapter;
    private final BaseAdapterDataModel<ProductDataModel> productAdapter;

    public LookBookDetailPresenterImpl(LookBookDetailArguments args) {
        view = args.getView();
        styleAdapter = args.getStyleAdapter();
        productAdapter = args.getProductAdapter();
    }

    @Override public void onCreate() {
        toRxObservable();
        view.setupStyleRecyclerView();
        view.setupProductRecyclerView();
    }

    @Override public void onBackClick() {
        RxBus.send(new LookBookPresenterImpl.RxEventScrollToPreview(true));
    }

    private void setupData(LookBookModuleDataModel data) {
        view.scrollToTop();
        view.setupLabel(data.getLabel());
        view.setupTitle(data.getTitle());
        view.setupTag(data.getTag());
        view.setupDesc(data.getDesc());

        styleAdapter.set(data.getStyles());
        view.styleRefresh();

        view.setupSimpleInfo(data.getSimple_info());

        productAdapter.set(data.getProducts());
        view.productRefresh();
    }

    private void toRxObservable() {
        addDisposable(RxBus.toObservable().subscribe(o -> {
            if (o instanceof LookBookPreviewPresenterImpl.RxEventLookBookModuleChanged) {
                LookBookModuleDataModel data =
                    ((LookBookPreviewPresenterImpl.RxEventLookBookModuleChanged) o).getData();
                setupData(data);
            } else if (o instanceof RxEventLookBookDetailScrollToTop) {
                view.scrollToTop();
            }
        }, Timber::e));
    }

    @AllArgsConstructor @Getter public final static class RxEventLookBookDetailScrollToTop {
    }
}