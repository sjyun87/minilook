package com.minilook.minilook.data.network.recent;

import com.minilook.minilook.data.model.base.BaseDataModel;
import io.reactivex.rxjava3.core.Single;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RecentService {

    @POST("/api/members/{memberNo}/recents") Single<BaseDataModel> getRecentProducts(
        @Path("memberNo") int memberNo,
        @Body RequestBody body
    );

    @DELETE("/api/members/{memberNo}/recents/{recentNo}") Single<BaseDataModel> deleteRecent(
        @Path("memberNo") int memberNo,
        @Path("recentNo") int recentNo
    );
}
