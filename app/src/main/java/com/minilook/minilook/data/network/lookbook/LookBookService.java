package com.minilook.minilook.data.network.lookbook;

import com.minilook.minilook.data.model.lookbook.LookBookDataModel;
import io.reactivex.rxjava3.core.Single;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LookBookService {

    @GET("minilookAction.do") Single<List<LookBookDataModel>> getLookBookModule(
        @Query("process") String process,
        @Query("start") int start,
        @Query("row") int row
    );
}
