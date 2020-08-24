package com.minilook.minilook.data.model.market;

import com.google.gson.JsonElement;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data public class MarketDataModel {
    @Expose @SerializedName("module_code")
    private String module;
    @Expose @SerializedName("title")
    private String title;
    @Expose @SerializedName("bold_description")
    private String bold_text;
    @Expose @SerializedName("viewType")
    private int visible_cnt;
    @Expose @SerializedName(value = "data", alternate = "products")
    private JsonElement data;

    // -- Controller
    private int type;
}
