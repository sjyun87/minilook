package com.minilook.minilook.data.model.shipping;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import lombok.Data;

@Data public class IslandDataModel {
    @Expose @SerializedName("extraShippingFee")
    private int island_shipping_price;
}
