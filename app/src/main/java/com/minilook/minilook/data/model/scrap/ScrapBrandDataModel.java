package com.minilook.minilook.data.model.scrap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.minilook.minilook.data.model.brand.BrandDataModel;
import java.util.List;
import lombok.Data;

@Data public class ScrapBrandDataModel {
    @Expose @SerializedName("totalPageSize")
    private int total;
    @Expose @SerializedName("brandScrabs")
    private List<BrandDataModel> brands;
}
