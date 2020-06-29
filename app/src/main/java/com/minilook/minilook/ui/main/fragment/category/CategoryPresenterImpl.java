package com.minilook.minilook.ui.main.fragment.category;

import com.minilook.minilook.data.model.category.CategoryDataModel;
import com.minilook.minilook.data.model.category.CategoryItemDataModel;
import com.minilook.minilook.data.network.category.CategoryRequest;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.main.fragment.category.di.CategoryArguments;

import timber.log.Timber;

public class CategoryPresenterImpl extends BasePresenterImpl implements CategoryPresenter {

    private final View view;
    private final BaseAdapterDataModel<CategoryItemDataModel> adapter;
    private final CategoryRequest categoryRequest;

    public CategoryPresenterImpl(CategoryArguments args) {
        view = args.getView();
        adapter = args.getAdapter();
        categoryRequest = new CategoryRequest();
    }

    @Override public void onCreate() {
        view.setupRecyclerView();

        reqCategoryList();
    }

    private void reqCategoryList() {
        addDisposable(
            categoryRequest.getCategoryList()
                .compose(Transformer.applySchedulers())
                .subscribe(this::resCategoryList, Timber::e)
        );
    }

    private void resCategoryList(CategoryDataModel data) {
        adapter.set(data.getDatas());
        view.refresh();
    }
}