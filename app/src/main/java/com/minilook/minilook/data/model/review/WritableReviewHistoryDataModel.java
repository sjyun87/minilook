package com.minilook.minilook.data.model.review;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.minilook.minilook.data.model.order.OrderHistoryDataModel;
import java.util.List;
import lombok.Data;

@Data public class WritableReviewHistoryDataModel {
    @Expose @SerializedName("ordersCount")
    private int orderCount;
    @Expose @SerializedName("orders")
    private List<OrderHistoryDataModel> orders;
}
