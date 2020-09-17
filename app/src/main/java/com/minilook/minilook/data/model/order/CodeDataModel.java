package com.minilook.minilook.data.model.order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import lombok.Data;

@Data public class CodeDataModel {
    @Expose @SerializedName("code")
    private String code;
    @Expose @SerializedName("codeName")
    private String codeName;
}
