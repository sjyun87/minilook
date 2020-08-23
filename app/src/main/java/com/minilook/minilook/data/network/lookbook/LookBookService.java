package com.minilook.minilook.data.network.lookbook;

import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.lookbook.LookBookDataModel;
import io.reactivex.rxjava3.core.Single;
import java.util.List;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LookBookService {

    @GET("/api/lookbooks") Single<BaseDataModel> getLookBookModule(
        @Query("pageSize") int row,
        @Query("usedLookbooks") RequestBody usedItems
    );
}
