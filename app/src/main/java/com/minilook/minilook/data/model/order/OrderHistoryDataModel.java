package com.minilook.minilook.data.model.order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.minilook.minilook.data.model.product.ProductDataModel;
import java.util.List;
import lombok.Data;

@Data public class OrderHistoryDataModel {
    @Expose @SerializedName("registDateName")
    private String orderDate;
    @Expose @SerializedName("mid")
    private String orderNo;
    @Expose @SerializedName("receiptId")
    private String receiptNo;
    @Expose @SerializedName("registDate")
    private long registDate;
    @Expose @SerializedName("products")
    private List<ProductDataModel> products;
}
