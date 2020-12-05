package com.minilook.minilook.data.network.event;

import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.network.base.BaseRequest;
import io.reactivex.rxjava3.core.Single;
import java.util.HashMap;
import java.util.Map;

public class EventRequest extends BaseRequest<EventService> {

    @Override protected Class<EventService> getService() {
        return EventService.class;
    }

    public Single<BaseDataModel> getEventDetail(int eventNo) {
        return getApi().getEventDetail(eventNo, createRequestBody(new HashMap<>()));
    }

    public Single<BaseDataModel> getEvents(int eventNo, int lastEventNo, int rows) {
        return getApi().getEvents(createRequestBody(createGetEventsData(eventNo, lastEventNo, rows)));
    }

    private Map<String, Object> createGetEventsData(int eventNo, int lastEventNo, int rows) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("pageSize", rows);
        if (lastEventNo > 0) jsonMap.put("pageEventNo", lastEventNo);
        jsonMap.put("eventNo", eventNo);
        return jsonMap;
    }
}