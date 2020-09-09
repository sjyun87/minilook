package com.minilook.minilook.data.network.ipage;

import com.minilook.minilook.data.model.base.BaseDataModel;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IpageService {

    @GET("/api/members/{user_id}") Single<BaseDataModel> getIpage(
        @Path("user_id") int userId
    );

    @GET("/api/members/{user_id}/points/histories") Single<BaseDataModel> getPointDetail(
        @Path("user_id") int userId
    );

    @GET("/api/members/{user_id}/coupons\n") Single<BaseDataModel> getCoupons(
        @Path("user_id") int userId
    );
}
