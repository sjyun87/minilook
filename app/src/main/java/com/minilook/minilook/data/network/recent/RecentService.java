package com.minilook.minilook.data.network.recent;

import com.minilook.minilook.data.model.base.BaseDataModel;
import io.reactivex.rxjava3.core.Single;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RecentService {

    @POST("/api/members/{user_id}/latestproducts") Single<BaseDataModel> getRecentProducts(
        @Path("user_id") int user_id,
        @Body RequestBody body
    );
}
