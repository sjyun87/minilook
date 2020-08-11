package com.minilook.minilook.ui.category;

import com.minilook.minilook.data.model.category.CategoryDataModel;
import com.minilook.minilook.data.network.category.CategoryRequest;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.category.di.CategoryArguments;
import java.util.List;
import timber.log.Timber;

public class CategoryPresenterImpl extends BasePresenterImpl implements CategoryPresenter {

    private final View view;
    private final BaseAdapterDataModel<CategoryDataModel> adapter;
    private final CategoryRequest categoryRequest;

    public CategoryPresenterImpl(CategoryArguments args) {
        view = args.getView();
        adapter = args.getAdapter();
        categoryRequest = new CategoryRequest();
    }

    @Override public void onCreate() {
        view.setupRecyclerView();

        reqCategory();
    }

    private void reqCategory() {
        addDisposable(
            categoryRequest.getCategoryList()
                .compose(Transformer.applySchedulers())
                .subscribe(this::resCategory, Timber::e)
        );
    }

    private void resCategory(List<CategoryDataModel> data) {
        adapter.set(data);
        view.refresh();
    }
}