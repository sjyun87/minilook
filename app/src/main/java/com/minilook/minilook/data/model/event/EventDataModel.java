package com.minilook.minilook.data.model.event;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data public class EventDataModel {
    @Expose @SerializedName("eventNo")
    private int event_id;
    @Expose @SerializedName("sumNameUrl")
    private String thumb_url;
    @Expose @SerializedName("url")
    private String event_url;
}
