package com.minilook.minilook.data.network.lookbook;

import com.minilook.minilook.data.model.base.BaseDataModel;
import io.reactivex.rxjava3.core.Single;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LookBookService {

    @POST("/api/lookbooks") Single<BaseDataModel> getLookBookModule(
        @Body RequestBody body
    );
}
