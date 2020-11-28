package com.minilook.minilook.data.network.category;

import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.network.base.BaseRequest;
import io.reactivex.rxjava3.core.Single;

public class CategoryRequest extends BaseRequest<CategoryService> {

    @Override protected Class<CategoryService> getService() {
        return CategoryService.class;
    }

    public Single<BaseDataModel> getCategories() {
        return getApi().getCategories();
    }
}
