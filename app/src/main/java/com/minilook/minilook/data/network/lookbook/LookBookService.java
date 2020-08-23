package com.minilook.minilook.data.network.lookbook;

import com.minilook.minilook.data.model.base.BaseDataModel;
import io.reactivex.rxjava3.core.Single;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;

public interface LookBookService {

    @GET("/api/lookbooks") Single<BaseDataModel> getLookBookModule(
        @Body RequestBody body
    );
}
