package com.minilook.minilook.data.network.event;

import com.minilook.minilook.data.model.base.BaseDataModel;
import io.reactivex.rxjava3.core.Single;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface EventService {

    @POST("/api/markets/events/{eventNo}") Single<BaseDataModel> getEventDetail(
        @Path("eventNo") int eventNo,
        @Body RequestBody body
    );

    @POST("/api/markets/withevents") Single<BaseDataModel> getEvents(
        @Body RequestBody body
    );
}
