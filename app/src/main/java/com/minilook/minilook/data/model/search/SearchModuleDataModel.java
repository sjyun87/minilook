package com.minilook.minilook.data.model.search;

import com.google.gson.JsonObject;

import java.util.List;

import lombok.Data;

@Data public class SearchModuleDataModel {
    private int module_type;
    private String title;
    private String subtitle;
    //private List<JsonObject> datas;
}
