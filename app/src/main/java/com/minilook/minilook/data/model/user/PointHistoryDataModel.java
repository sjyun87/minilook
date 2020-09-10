package com.minilook.minilook.data.model.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import lombok.Data;

@Data public class PointHistoryDataModel {
    @Expose @SerializedName("memberNo")
    private int point;
    @Expose @SerializedName("nickname")
    private List<PointDataModel> history;
}
