package com.minilook.minilook.data.network.category;

import com.minilook.minilook.data.model.category.CategoryDataModel;
import com.minilook.minilook.data.network.base.BaseRequest;

import io.reactivex.rxjava3.core.Single;

public class CategoryRequest extends BaseRequest<CategoryService> {

    @Override protected Class<CategoryService> getService() {
        return CategoryService.class;
    }

    public Single<CategoryDataModel> getCategoryList() {
        return getApi().getCategoryList("Category", "02");
    }
}
