package com.minilook.minilook.data.model.market;

import com.google.gson.JsonObject;

import java.util.List;

import lombok.Data;

@Data public class MarketModuleDataModel {
    private int module_type;
    private String title;
    private String subtitle;
    private boolean is_total;
    private List<JsonObject> datas;
}
