package com.minilook.minilook.data.model.lookbook;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import lombok.Data;

@Data public class LookBookDataModel {
    @Expose @SerializedName("isReset")
    private boolean isReset;
    @Expose @SerializedName("lookbooks")
    private List<LookBookModuleDataModel> lookbooks;
}
