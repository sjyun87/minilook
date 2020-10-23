package com.minilook.minilook.data.model.product;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.minilook.minilook.data.model.base.BooleanDeserializer;
import java.util.List;
import lombok.Data;

@Data public class OptionDataModel {
    @Expose @SerializedName("products")
    private List<OptionProductDataModel> products;
    @Expose @SerializedName("bonusProducts")
    private List<OptionProductDataModel> bonusProducts;
}