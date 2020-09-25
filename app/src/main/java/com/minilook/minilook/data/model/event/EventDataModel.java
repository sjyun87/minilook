package com.minilook.minilook.data.model.event;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data public class EventDataModel {
    @Expose @SerializedName("eventNo")
    private int eventNo;
    @Expose @SerializedName("sumNameUrl")
    private String thumbUrl;
    @Expose @SerializedName("url")
    private String eventUrl;
}
