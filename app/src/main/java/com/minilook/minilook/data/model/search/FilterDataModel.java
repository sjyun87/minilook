package com.minilook.minilook.data.model.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.minilook.minilook.data.model.common.ColorDataModel;
import com.minilook.minilook.data.model.common.GenderDataModel;
import com.minilook.minilook.data.model.common.StyleDataModel;
import com.minilook.minilook.data.model.product.ProductDataModel;
import java.util.List;
import lombok.Data;

@Data public class FilterDataModel {
    @Expose @SerializedName("sytles")
    private List<StyleDataModel> styles;
    @Expose @SerializedName("genders")
    private List<GenderDataModel> genders;
    @Expose @SerializedName("colors")
    private List<ColorDataModel> colors;
}
