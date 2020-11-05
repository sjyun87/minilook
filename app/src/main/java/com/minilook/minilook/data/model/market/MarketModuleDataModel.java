package com.minilook.minilook.data.model.market;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.minilook.minilook.data.model.common.CodeDataModel;
import com.minilook.minilook.data.model.product.ProductDataModel;
import java.util.List;
import lombok.Data;

@Data public class MarketModuleDataModel {
    @Expose @SerializedName("tabs")
    private List<CodeDataModel> tabs;
    @Expose @SerializedName("products")
    private List<ProductDataModel> products;
    @Expose @SerializedName("tags")
    private List<String> tags;
}
