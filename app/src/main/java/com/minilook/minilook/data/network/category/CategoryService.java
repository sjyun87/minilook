package com.minilook.minilook.data.network.category;

import com.minilook.minilook.data.model.base.BaseDataModel;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;

public interface CategoryService {

    @GET("/api/commons/categories") Single<BaseDataModel> getCategories();
}
