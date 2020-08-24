package com.minilook.minilook.data.network.lookbook;

import com.minilook.minilook.data.model.base.BaseDataModel;
import io.reactivex.rxjava3.core.Single;
import java.util.List;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LookBookService {

    @POST("/api/lookbooks") Single<BaseDataModel> getLookBookModule(
        @Body RequestBody body
    );
}
