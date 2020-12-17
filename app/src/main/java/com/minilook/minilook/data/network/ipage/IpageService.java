package com.minilook.minilook.data.network.ipage;

import com.minilook.minilook.data.model.base.BaseDataModel;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IpageService {

    @GET("/api/members/{memberNo}") Single<BaseDataModel> getIpage(
        @Path("memberNo") int memberNo
    );

    @GET("/api/members/{memberNo}/points/histories") Single<BaseDataModel> getPointHistory(
        @Path("memberNo") int memberNo
    );

    @GET("/api/members/{memberNo}/coupons") Single<BaseDataModel> getCoupons(
        @Path("memberNo") int memberNo
    );
}
