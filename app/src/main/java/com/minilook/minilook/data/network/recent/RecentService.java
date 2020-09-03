package com.minilook.minilook.data.network.recent;

import com.minilook.minilook.data.model.base.BaseDataModel;
import io.reactivex.rxjava3.core.Single;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RecentService {

    @POST("/api/members/{user_id}/recents") Single<BaseDataModel> getRecentProducts(
        @Path("user_id") int user_id,
        @Body RequestBody body
    );

    @DELETE("/api/members/{user_id}/recents/{recent_id}") Single<BaseDataModel> deleteRecent(
        @Path("user_id") int user_id,
        @Path("recent_id") int recent_id
    );
}
