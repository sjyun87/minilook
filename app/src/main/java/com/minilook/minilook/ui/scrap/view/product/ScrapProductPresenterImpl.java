package com.minilook.minilook.ui.scrap.view.product;

import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.product.adapter.ProductAdapter;
import com.minilook.minilook.ui.scrap.view.product.di.ScrapProductArguments;

public class ScrapProductPresenterImpl extends BasePresenterImpl implements ScrapProductPresenter {

    private final View view;
    private final BaseAdapterDataModel<ProductDataModel> adapter;

    public ScrapProductPresenterImpl(ScrapProductArguments args) {
        view = args.getView();
        adapter = args.getAdapter();
    }

    @Override public void onCreate() {
        view.setupRecyclerView();
    }
}