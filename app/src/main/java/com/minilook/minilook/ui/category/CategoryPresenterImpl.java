package com.minilook.minilook.ui.category;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.common.URLKeys;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.common.CategoryDataModel;
import com.minilook.minilook.data.network.category.CategoryRequest;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.category.di.CategoryArguments;
import io.reactivex.rxjava3.functions.Function;
import java.util.ArrayList;
import java.util.List;
import timber.log.Timber;

public class CategoryPresenterImpl extends BasePresenterImpl implements CategoryPresenter {

    private final View view;
    private final BaseAdapterDataModel<CategoryDataModel> adapter;
    private final CategoryRequest categoryRequest;

    private Gson gson = new Gson();

    public CategoryPresenterImpl(CategoryArguments args) {
        view = args.getView();
        adapter = args.getAdapter();
        categoryRequest = new CategoryRequest();
    }

    @Override public void onCreate() {
        view.setupRecyclerView();

        reqCategory();
    }

    @Override public void onBannerVideoClick() {
        view.navigateToYoutube(URLKeys.URL_YOUTUBE);
    }

    private void reqCategory() {
        addDisposable(
            categoryRequest.getCategoryList()
                .compose(Transformer.applySchedulers())
                .filter(data -> data.getCode().equals(HttpCode.OK))
                .map((Function<BaseDataModel, List<CategoryDataModel>>)
                    data -> gson.fromJson(data.getData(), new TypeToken<ArrayList<CategoryDataModel>>() {
                    }.getType()))
                .subscribe(this::resCategory, Timber::e)
        );
    }

    private void resCategory(List<CategoryDataModel> data) {
        adapter.set(data);
        view.refresh();
    }
}