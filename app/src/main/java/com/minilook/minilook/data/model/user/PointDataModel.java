package com.minilook.minilook.data.model.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data public class PointDataModel {
    @Expose @SerializedName("memberNo")
    private int user_id;
    @Expose @SerializedName("nickname")
    private String nick;
}
