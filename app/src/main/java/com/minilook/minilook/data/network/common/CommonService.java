package com.minilook.minilook.data.network.common;

import com.minilook.minilook.data.model.base.BaseDataModel;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;

public interface CommonService {

    @GET("/api/commons/orderbys") Single<BaseDataModel> getSortCode();
}
