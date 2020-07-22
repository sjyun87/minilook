package com.minilook.minilook.data.network.category;

import com.minilook.minilook.data.model.category.CategoryTestDataModel;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CategoryService {

    @GET("minilookAction.do") Single<CategoryTestDataModel> getCategoryList(
        @Query("process") String process,
        @Query("class_code") String classCode
    );
}
