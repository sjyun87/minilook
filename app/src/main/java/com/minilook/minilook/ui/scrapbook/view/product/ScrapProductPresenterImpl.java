package com.minilook.minilook.ui.scrapbook.view.product;

import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.scrapbook.view.product.di.ScrapbookProductArguments;

public class ScrapProductPresenterImpl extends BasePresenterImpl implements ScrapProductPresenter {

    private final View view;
    private final BaseAdapterDataModel<ProductDataModel> adapter;

    public ScrapProductPresenterImpl(ScrapbookProductArguments args) {
        view = args.getView();
        adapter = args.getAdapter();
    }

    @Override public void onCreate() {
        view.setupRecyclerView();
    }
}