package com.minilook.minilook.data.model.order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.data.model.shipping.ShippingDataModel;
import java.util.List;
import lombok.Data;

@Data public class OrderDetailDataModel {
    @Expose @SerializedName("address")
    private ShippingDataModel shippingData;
    @Expose @SerializedName("order")
    private OrderDataModel orderData;
}
