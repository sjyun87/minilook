package com.minilook.minilook.ui.main.fragment.lookbook.view.detail;

import com.minilook.minilook.data.model.lookbook.LookBookDetailDataModel;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.main.fragment.lookbook.view.detail.di.LookBookDetailArguments;
import com.minilook.minilook.ui.main.fragment.lookbook.view.preview.LookBookPreviewPresenterImpl;
import java.util.List;
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

    private void toRxObservable() {
        addDisposable(RxBus.toObservable().subscribe(o -> {
            if (o instanceof LookBookPreviewPresenterImpl.RxEventLookBookModuleChanged) {
                LookBookDetailDataModel data = ((LookBookPreviewPresenterImpl.RxEventLookBookModuleChanged) o).getData();
                setupData(data);
            }
        }, Timber::e));
    }

    private void setupData(LookBookDetailDataModel data) {
        view.setupLabel(data.getLabel());
        view.setupTitle(data.getTitle());
        view.setupTag(data.getTag());
        view.setupDescription(data.getDesc());
        styleAdapter.set(data.getImage_urls());
        view.styleRefresh();
        view.setupProductInfo(parseToProductInfo(data.getProducts()));
        productAdapter.set(data.getProducts());
        view.productRefresh();
    }

    private String parseToProductInfo(List<ProductDataModel> products) {
        StringBuilder sb = new StringBuilder();
        for (ProductDataModel model : products) {
            if (sb.length() != 0) sb.append(" ");
            String category = model.getCategory_name();
            String name = model.getName();
            int price = model.getPrice() / 1000;
            String brand = model.getBrand().getName();
            sb.append(String.format("[%s]%s %d만원대 by%s", category, name, price, brand));
        }
        return sb.toString();
    }
}