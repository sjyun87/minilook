package com.minilook.minilook.data.model.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.minilook.minilook.data.model.product.ProductDataModel;
import java.util.List;
import lombok.Data;

@Data public class SearchDataModel {
    @Expose @SerializedName("totalPageSize")
    private int total;
    @Expose @SerializedName("products")
    private List<ProductDataModel> products;
}
