package com.minilook.minilook.data.model.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import lombok.Data;

@Data public class PointDataModel {
    @Expose @SerializedName("totalPoint")
    private int point;
    @Expose @SerializedName("histories")
    private List<PointHistoryDataModel> history;
}
