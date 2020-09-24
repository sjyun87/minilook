package com.minilook.minilook.data.model.order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.minilook.minilook.data.model.product.ProductDataModel;
import java.util.List;
import lombok.Data;

@Data public class OrderHistoryDataModel {
    @Expose @SerializedName("registDateName")
    private String order_date;
    @Expose @SerializedName("mid")
    private String order_id;
    @Expose @SerializedName("receiptId")
    private String receipt_id;
    @Expose @SerializedName("registDate")
    private long regist_date;
    @Expose @SerializedName("products")
    private List<ProductDataModel> products;
}
