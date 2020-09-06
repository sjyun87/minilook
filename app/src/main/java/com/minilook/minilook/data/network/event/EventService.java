package com.minilook.minilook.data.network.event;

import com.minilook.minilook.data.model.base.BaseDataModel;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface EventService {

    @GET("/api/markets/events/{event_id}") Single<BaseDataModel> getEventDetail(
        @Path("event_id") int event_id
    );
}
