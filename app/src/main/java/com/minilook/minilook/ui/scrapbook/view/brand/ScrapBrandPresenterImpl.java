package com.minilook.minilook.ui.scrapbook.view.brand;

import com.minilook.minilook.data.model.preorder.PreorderDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.scrapbook.view.brand.di.ScrapbookBrandArguments;

public class ScrapBrandPresenterImpl extends BasePresenterImpl implements ScrapBrandPresenter {

    private final View view;
    private final BaseAdapterDataModel<PreorderDataModel> adapter;

    public ScrapBrandPresenterImpl(ScrapbookBrandArguments args) {
        view = args.getView();
        adapter = args.getAdapter();
    }

    @Override public void onCreate() {
        view.setupRecyclerView();
    }
}