package com.minilook.minilook.data.model.market;

import com.google.gson.JsonElement;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data public class MarketDataModel {
    @Expose @SerializedName("moduleCode")
    private String type;
    @Expose @SerializedName("title")
    private String title;
    @Expose @SerializedName("data")
    private JsonElement data;

    // -- Optional
    private boolean isRefreshing;
}
