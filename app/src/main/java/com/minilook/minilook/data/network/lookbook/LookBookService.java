package com.minilook.minilook.data.network.lookbook;

import com.minilook.minilook.data.model.lookbook.LookBookTestDataModel;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LookBookService {

    @GET("minilookAction.do") Single<LookBookTestDataModel> getLookBookModule(
        @Query("process") String process,
        @Query("start") int start,
        @Query("row") int row
    );
}
