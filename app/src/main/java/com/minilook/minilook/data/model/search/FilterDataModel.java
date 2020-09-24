package com.minilook.minilook.data.model.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.minilook.minilook.data.model.common.CategoryDataModel;
import com.minilook.minilook.data.model.common.ColorDataModel;
import com.minilook.minilook.data.model.common.GenderDataModel;
import com.minilook.minilook.data.model.common.StyleDataModel;
import java.util.List;
import lombok.Data;

@Data public class FilterDataModel {
    @Expose @SerializedName("sytles")
    private List<StyleDataModel> styles;
    @Expose @SerializedName("genders")
    private List<GenderDataModel> genders;
    @Expose @SerializedName("categories")
    private List<CategoryDataModel> categories;
    @Expose @SerializedName("colors")
    private List<ColorDataModel> colors;
    @Expose @SerializedName("minPrice")
    private int price_min;
    @Expose @SerializedName("maxPrice")
    private int price_max;
}
