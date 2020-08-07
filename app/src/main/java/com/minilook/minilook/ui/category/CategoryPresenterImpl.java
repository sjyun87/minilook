package com.minilook.minilook.ui.category;

import com.minilook.minilook.data.model.category.CategoryDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.category.di.CategoryArguments;
import java.util.ArrayList;
import java.util.List;

public class CategoryPresenterImpl extends BasePresenterImpl implements CategoryPresenter {

    private final View view;
    private final BaseAdapterDataModel<CategoryDataModel> adapter;

    public CategoryPresenterImpl(CategoryArguments args) {
        view = args.getView();
        adapter = args.getAdapter();
    }

    @Override public void onCreate() {
        view.setupRecyclerView();

        // 임시..
        setupData();
    }

    private void setupData() {
        List<CategoryDataModel> items = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            CategoryDataModel model = new CategoryDataModel();
            model.setName("베이비");
            items.add(model);
        }

        adapter.set(items);
        view.refresh();
    }
}