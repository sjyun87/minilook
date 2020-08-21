package com.minilook.minilook.ui.scrap.view.brand;

import com.minilook.minilook.data.model.preorder.PreorderDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.scrap.view.brand.di.ScrapBrandArguments;
import java.util.ArrayList;
import java.util.List;

public class ScrapBrandPresenterImpl extends BasePresenterImpl implements ScrapBrandPresenter {

    private final View view;
    private final BaseAdapterDataModel<PreorderDataModel> adapter;

    public ScrapBrandPresenterImpl(ScrapBrandArguments args) {
        view = args.getView();
        adapter = args.getAdapter();
    }

    @Override public void onCreate() {
        view.setupRecyclerView();
    }
}