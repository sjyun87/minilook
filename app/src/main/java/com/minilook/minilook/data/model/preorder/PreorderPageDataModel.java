package com.minilook.minilook.data.model.preorder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import lombok.Data;

@Data public class PreorderPageDataModel {
    @Expose @SerializedName("totalPageSize")
    private int total;
    @Expose @SerializedName("ends")
    private List<PreorderDataModel> preorders;
}
