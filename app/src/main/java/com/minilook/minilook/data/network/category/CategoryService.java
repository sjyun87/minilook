package com.minilook.minilook.data.network.category;

import com.minilook.minilook.data.model.category.CategoryDataModel;
import io.reactivex.rxjava3.core.Single;
import java.util.List;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface CategoryService {

    @POST("api/category") Single<List<CategoryDataModel>> getCategoryList();
}
