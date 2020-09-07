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

    public Single<BaseDataModel> getEventDetail(int event_id) {
        return getApi().getEventDetail(event_id, createRequestBody(new HashMap<>()));
    }

    public Single<BaseDataModel> getEvents(int latest_id, int rows) {
        return getApi().getEvents(createRequestBody(parseToJson(latest_id, rows)));
    }

    private Map<String, Object> parseToJson(int latest_id, int rows) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("pageSize", rows);
        jsonMap.put("pageEventNo", latest_id);
        return jsonMap;
    }
}
