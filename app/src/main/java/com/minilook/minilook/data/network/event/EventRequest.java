package com.minilook.minilook.data.network.event;

import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.network.base.BaseRequest;
import io.reactivex.rxjava3.core.Single;

public class EventRequest extends BaseRequest<EventService> {

    @Override protected Class<EventService> getService() {
        return EventService.class;
    }

    public Single<BaseDataModel> getEventDetail(int event_id) {
        return getApi().getEventDetail(event_id);
    }

    //public Single<BaseDataModel> getPromotions(int promotion_id, int rows, int latestPromotionId) {
    //    return getApi().getPromotions(createRequestBody(parseToTogetherJson(promotion_id, rows, latestPromotionId)));
    //}
    //
    //private Map<String, Object> parseToTogetherJson(int promotion_id, int rows, int latest_id) {
    //    Map<String, Object> jsonMap = new HashMap<>();
    //    if (latest_id != -1) jsonMap.put("pagePromotionNo", latest_id);
    //    jsonMap.put("pageSize", rows);
    //    jsonMap.put("promotionNo", promotion_id);
    //    return jsonMap;
    //}
}
