package com.minilook.minilook.ui.lookbook.view.detail;

import com.minilook.minilook.data.model.lookbook.LookBookModuleDataModel;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.lookbook.LookBookPresenterImpl;
import com.minilook.minilook.ui.lookbook.view.detail.di.LookBookDetailArguments;
import com.minilook.minilook.ui.lookbook.view.preview.LookBookPreviewPresenterImpl;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import timber.log.Timber;

public class LookBookDetailPresenterImpl extends BasePresenterImpl implements LookBookDetailPresenter {

    private final View view;
    private final BaseAdapterDataModel<String> styleAdapter;
    private final BaseAdapterDataModel<ProductDataModel> productAdapter;

    private LookBookModuleDataModel data;

    public LookBookDetailPresenterImpl(LookBookDetailArguments args) {
        view = args.getView();
        styleAdapter = args.getStyleAdapter();
        productAdapter = args.getProductAdapter();
    }

    @Override public void onCreateView() {
        toRxObservable();
        view.setupTitleBar();
        view.setupStyleRecyclerView();
        view.setupProductRecyclerView();
    }

    @Override public void onBackClick() {
        RxBus.send(new LookBookPresenterImpl.RxEventScrollToPreview(true));
    }

    private void setupData() {
        view.scrollToTop();

        view.setLabel(data.getLabel());
        view.setTitle(data.getTitle());
        view.setTag(data.getTag());
        view.setDesc(data.getDesc());

        styleAdapter.set(data.getStyles());
        view.styleRefresh();

        view.setSimpleInfo(data.getSimpleInfo());

        productAdapter.set(data.getProducts());
        view.productRefresh();
    }

    private void replaceProductData(List<ProductDataModel> products) {
        productAdapter.set(products);
        view.productRefresh();
    }

    private void toRxObservable() {
        addDisposable(RxBus.toObservable().subscribe(o -> {
            if (o instanceof LookBookPreviewPresenterImpl.RxEventLookBookModuleChanged) {
                data = ((LookBookPreviewPresenterImpl.RxEventLookBookModuleChanged) o).getData();
                setupData();
            } else if (o instanceof RxEventLookBookDetailScrollToTop) {
                view.scrollToTop();
            } else if (o instanceof RxEventLookBookDetailUpdateScrap) {
                LookBookModuleDataModel data = ((RxEventLookBookDetailUpdateScrap) o).getData();
                replaceProductData(data.getProducts());
            }
        }, Timber::e));
    }

    @AllArgsConstructor @Getter public final static class RxEventLookBookDetailScrollToTop {
    }

    @AllArgsConstructor @Getter public final static class RxEventLookBookDetailUpdateScrap {
        private final LookBookModuleDataModel data;
    }
}