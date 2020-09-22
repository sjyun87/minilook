package com.minilook.minilook.data.model.order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.minilook.minilook.data.model.common.CodeDataModel;
import java.util.List;
import lombok.Data;

@Data public class ExchangeNReturnDataModel {
    @Expose @SerializedName("types")
    private List<CodeDataModel> types;
    @Expose @SerializedName("reasons")
    private List<CodeDataModel> reasons;
}
