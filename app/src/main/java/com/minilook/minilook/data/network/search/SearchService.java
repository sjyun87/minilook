package com.minilook.minilook.data.network.search;

import com.minilook.minilook.data.model.search.SearchDataModel;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SearchService {

    @GET("minilookAction.do") Single<SearchDataModel> getSearchModule(
        @Query("process") String process
    );
}
