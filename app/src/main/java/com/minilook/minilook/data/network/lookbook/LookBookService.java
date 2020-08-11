package com.minilook.minilook.data.network.lookbook;

import com.minilook.minilook.data.model.lookbook.LookBookDataModel;
import io.reactivex.rxjava3.core.Single;
import java.util.List;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LookBookService {

    @POST("/api/lookbooks") Single<List<LookBookDataModel>> getLookBookModule(
        @Body RequestBody body
    );
}
