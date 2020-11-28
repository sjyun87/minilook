package com.minilook.minilook.data.network.search;

import com.minilook.minilook.data.model.base.BaseDataModel;
import io.reactivex.rxjava3.core.Single;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SearchService {

    @POST("/api/products") Single<BaseDataModel> getProducts(
        @Body RequestBody requestBody
    );

    @GET("/api/commons/filters/options/{categoryCode}") Single<BaseDataModel> getFilterOptions(
        @Path("categoryCode") String code
    );
}
