package com.minilook.minilook.data.model.scrap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.minilook.minilook.data.model.product.ProductDataModel;
import java.util.List;
import lombok.Data;

@Data public class ScrapProductDataModel {
    @Expose @SerializedName("totalPageSize")
    private int total;
    @Expose @SerializedName("productScrabs")
    private List<ProductDataModel> products;
}
