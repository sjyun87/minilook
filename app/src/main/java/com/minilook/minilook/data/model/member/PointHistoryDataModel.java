package com.minilook.minilook.data.model.member;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import lombok.Data;

@Data public class PointHistoryDataModel {
    @Expose @SerializedName("totalPoint")
    private int point;
    @Expose @SerializedName("histories")
    private List<PointDataModel> history;
}
