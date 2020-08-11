package com.minilook.minilook.data.model.market;

import com.google.gson.JsonObject;
import java.util.List;
import lombok.Data;

@Data public class MarketDataModel {
    private int type;
    private String title;
    private List<JsonObject> data;
    //private List
}
