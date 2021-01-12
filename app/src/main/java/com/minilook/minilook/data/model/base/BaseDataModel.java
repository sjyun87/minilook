package com.minilook.minilook.data.model.base;

import com.google.gson.JsonElement;
import lombok.Data;

@Data public class BaseDataModel {
    private String code;
    private String message;
    private JsonElement data;
    private int totalCount;
    private int totalPage;
}